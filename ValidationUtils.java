package util;

import java.math.BigInteger;
import java.net.IDN;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class with a wide range of static methods to validate common data types and formats:
 * <ul>
 *   <li>Non‐empty strings</li>
 *   <li>Email addresses</li>
 *   <li>Spanish phone numbers</li>
 *   <li>Spanish license plates</li>
 *   <li>DNI/NIE</li>
 *   <li>Names</li>
 *   <li>Integers and decimals</li>
 *   <li>Postal codes (ES)</li>
 *   <li>URLs</li>
 *   <li>IPv4 and IPv6</li>
 *   <li>Hex colors and MAC addresses</li>
 *   <li>Credit cards (Luhn) and IBAN</li>
 *   <li>ISBN‐10 and ISBN‐13</li>
 *   <li>UUIDs</li>
 *   <li>Dates, times and datetimes</li>
 * </ul>
 */
public class ValidationUtils {

    // ---- precompiled regex patterns ----
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PHONE_ES_PATTERN = Pattern.compile(
        "^(?:\\+34)?[6789]\\d{8}$");
    private static final Pattern PLATE_ES_PATTERN = Pattern.compile(
        "^\\d{4}[B-DF-HJ-NP-TV-Z]{3}$");
    private static final Pattern NAME_PATTERN = Pattern.compile(
        "^[A-Za-zÁÉÍÓÚáéíóúÑñÜü'\\- ]+$");
    private static final Pattern INTEGER_PATTERN = Pattern.compile(
        "^-?\\d+$");
    private static final Pattern DECIMAL_PATTERN = Pattern.compile(
        "^-?\\d+(?:[\\.,]\\d+)?$");
    private static final Pattern POSTCODE_ES_PATTERN = Pattern.compile(
        "^(0[1-9]|[1-4]\\d|5[0-2])\\d{3}$");
    private static final Pattern URL_PATTERN = Pattern.compile(
        "^(https?://)"
      + "(([\\w\\-~]+\\.)+[\\w\\-~]+|"                        // domain...
      + "localhost|"                                          // localhost...
      + "\\d{1,3}(\\.\\d{1,3}){3})"                           // ...or IPv4
      + "(:\\d+)?(/\\S*)?$",                                  // optional port & path
        Pattern.CASE_INSENSITIVE);
    private static final Pattern IPV4_PATTERN = Pattern.compile(
        "^(25[0-5]|2[0-4]\\d|[01]?\\d?\\d)(\\.(?!$)|$){4}$");
    private static final Pattern IPV6_PATTERN = Pattern.compile(
        "^(?:[0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4}$");
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile(
        "^#?[A-Fa-f0-9]{6}$");
    private static final Pattern MAC_PATTERN = Pattern.compile(
        "^([0-9A-Fa-f]{2}[:-]){5}[0-9A-Fa-f]{2}$");
    private static final Pattern IBAN_PATTERN = Pattern.compile(
        "^[A-Z]{2}\\d{2}[A-Z0-9]{1,30}$");
    private static final Pattern ISBN10_PATTERN = Pattern.compile(
        "^(?:\\d{9}X|\\d{10})$");
    private static final Pattern ISBN13_PATTERN = Pattern.compile(
        "^\\d{13}$");
    private static final Pattern UUID_PATTERN = Pattern.compile(
        "^[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-"
      + "[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}$");

    // ---- basic checks ----

    /** Returns true if the string is not null and contains non‐whitespace characters. */
    public static boolean isNotBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

    /** Validates an email address (basic RFC‐style check). */
    public static boolean isEmail(String email) {
        if (email == null) return false;
        Matcher m = EMAIL_PATTERN.matcher(email);
        return m.matches();
    }

    /** Validates a Spanish phone number (optionally +34, then 9 digits starting with 6‐9). */
    public static boolean isPhoneEs(String phone) {
        if (phone == null) return false;
        return PHONE_ES_PATTERN.matcher(phone).matches();
    }

    /** Validates a modern Spanish license plate (4 digits + 3 letters, no vowels). */
    public static boolean isSpanishPlate(String plate) {
        if (plate == null) return false;
        return PLATE_ES_PATTERN.matcher(plate).matches();
    }

    /** Validates DNI or NIE (including checksum letter). */
    public static boolean isDniNie(String dni) {
        if (dni == null) return false;
        String t = dni.toUpperCase().trim();
        // NIE: starts X,Y,Z
        if (t.matches("^[XYZ]\\d{7}[A-Z]$")) {
            char first = t.charAt(0);
            t = (first=='X'?'0': first=='Y'?'1':'2') + t.substring(1);
        }
        // DNI: 8 digits + letter
        if (!t.matches("^\\d{8}[A-Z]$")) {
            return false;
        }
        String number = t.substring(0, 8);
        char letter = t.charAt(8);
        String control = "TRWAGMYFPDXBNJZSQVHLCKE";
        int idx = Integer.parseInt(number) % 23;
        return letter == control.charAt(idx);
    }

    /** Validates a personal name (letters, spaces, hyphens, apostrophes, accents). */
    public static boolean isName(String name) {
        if (name == null) return false;
        return NAME_PATTERN.matcher(name).matches();
    }

    /** Validates an integer (optional leading minus). */
    public static boolean isInteger(String s) {
        if (s == null) return false;
        return INTEGER_PATTERN.matcher(s.trim()).matches();
    }

    /** Validates a decimal number (dot or comma separator). */
    public static boolean isDecimal(String s) {
        if (s == null) return false;
        return DECIMAL_PATTERN.matcher(s.trim()).matches();
    }

    /** Validates a Spanish postal code (01000–52999). */
    public static boolean isPostalCodeEs(String cp) {
        if (cp == null) return false;
        return POSTCODE_ES_PATTERN.matcher(cp.trim()).matches();
    }

    // ---- network & color ----

    /** Validates a URL (http/https, domain or IPv4, optional port/path). */
    public static boolean isUrl(String url) {
        if (url == null) return false;
        return URL_PATTERN.matcher(url.trim()).matches();
    }

    /** Validates an IPv4 address. */
    public static boolean isIPv4(String ip) {
        if (ip == null) return false;
        return IPV4_PATTERN.matcher(ip.trim()).matches();
    }

    /** Validates a simple IPv6 address (eight groups of 1–4 hex digits). */
    public static boolean isIPv6(String ip) {
        if (ip == null) return false;
        return IPV6_PATTERN.matcher(ip.trim()).matches();
    }

    /** Validates a hex color code (#RRGGBB or RRGGBB). */
    public static boolean isHexColor(String hex) {
        if (hex == null) return false;
        return HEX_COLOR_PATTERN.matcher(hex.trim()).matches();
    }

    /** Validates a MAC address (six pairs of hex digits). */
    public static boolean isMacAddress(String mac) {
        if (mac == null) return false;
        return MAC_PATTERN.matcher(mac.trim()).matches();
    }

    // ---- algorithmic validations ----

    /**
     * Validates a credit card number using the Luhn algorithm.
     */
    public static boolean isCreditCard(String cc) {
        if (cc == null) return false;
        String digits = cc.replaceAll("\\s+", "");
        if (!digits.matches("\\d{13,19}")) return false;
        int sum = 0, alt = 0;
        for (int i = digits.length() - 1; i >= 0; i--) {
            int d = digits.charAt(i) - '0';
            if (alt++ % 2 == 1) {
                d *= 2;
                if (d > 9) d -= 9;
            }
            sum += d;
        }
        return sum % 10 == 0;
    }

    /**
     * Validates an IBAN (generic check: country code + check digits + BBAN, mod 97).
     */
    public static boolean isIban(String iban) {
        if (iban == null) return false;
        String s = iban.replaceAll("\\s+", "").toUpperCase();
        if (!IBAN_PATTERN.matcher(s).matches()) return false;
        // move first 4 chars to end
        String reform = s.substring(4) + s.substring(0, 4);
        // convert letters to numbers: A=10, B=11...
        StringBuilder numeric = new StringBuilder();
        for (char c : reform.toCharArray()) {
            if (Character.isLetter(c)) {
                numeric.append(c - 'A' + 10);
            } else {
                numeric.append(c);
            }
        }
        BigInteger num = new BigInteger(numeric.toString());
        return num.mod(BigInteger.valueOf(97)).intValue() == 1;
    }

    /** Validates ISBN‐10 (with optional trailing X). */
    public static boolean isIsbn10(String isbn) {
        if (isbn == null) return false;
        String s = isbn.replaceAll("[\\-\\s]", "");
        if (!ISBN10_PATTERN.matcher(s).matches()) return false;
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (i + 1) * Character.getNumericValue(s.charAt(i));
        }
        char last = s.charAt(9);
        sum += (last == 'X' ? 10 : Character.getNumericValue(last)) * 10;
        return sum % 11 == 0;
    }

    /** Validates ISBN‐13. */
    public static boolean isIsbn13(String isbn) {
        if (isbn == null) return false;
        String s = isbn.replaceAll("[\\-\\s]", "");
        if (!ISBN13_PATTERN.matcher(s).matches()) return false;
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int v = Character.getNumericValue(s.charAt(i));
            sum += (i % 2 == 0 ? v : v * 3);
        }
        int check = (10 - (sum % 10)) % 10;
        return check == Character.getNumericValue(s.charAt(12));
    }

    /** Validates a UUID (versions 1–5). */
    public static boolean isUuid(String uuid) {
        if (uuid == null) return false;
        return UUID_PATTERN.matcher(uuid.trim()).matches();
    }

    // ---- date/time ----

    /**
     * Validates a date string against the given pattern (e.g. "yyyy-MM-dd").
     */
    public static boolean isValidDate(String date, String pattern) {
        if (date == null) return false;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
            LocalDate.parse(date, fmt);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    /** Shortcut for ISO date "yyyy-MM-dd". */
    public static boolean isValidDate(String isoDate) {
        return isValidDate(isoDate, "yyyy-MM-dd");
    }

    /**
     * Validates a time string against the given pattern (e.g. "HH:mm" or "HH:mm:ss").
     */
    public static boolean isValidTime(String time, String pattern) {
        if (time == null) return false;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
            LocalTime.parse(time, fmt);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    /** Shortcut for "HH:mm". */
    public static boolean isValidTime(String hhmm) {
        return isValidTime(hhmm, "HH:mm");
    }

    /**
     * Validates a date‐time string against the given pattern
     * (e.g. "yyyy-MM-dd HH:mm:ss" or ISO "yyyy-MM-dd'T'HH:mm:ss").
     */
    public static boolean isValidDateTime(String dt, String pattern) {
        if (dt == null) return false;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime.parse(dt, fmt);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    /** Shortcut for ISO "yyyy-MM-dd'T'HH:mm:ss". */
    public static boolean isValidDateTime(String isoDateTime) {
        return isValidDateTime(isoDateTime, "yyyy-MM-dd'T'HH:mm:ss");
    }
}
