package ee.ttu.iti0202.calculator;

public class Calculator {
    public static void main(String[] args) {
    }
    private enum LongerPad { LEFT, RIGHT }

    /**
     * Return a string following a naming convention.
     * [first three letters in uppercase]-[length of string][last two letters of string in lowercase]
     * If length of string is less than 3, return "ERROR".
     *
     * @param s original name
     */
    private static String convertName(String s) {
        if (s.length() < 3) {
            return "ERROR";
        }
        String first = s.substring(0, 3).toUpperCase();
        String end = s.substring(s.length() - 2).toLowerCase();
        return String.format("%s-%d%s", first, s.length(), end);
        //String.format("%2$s", 32, "Hello"); // prints: "Hello"
    }

    /**
     * Return an expression that sums the numbers a and b.
     * Example: a = 3, b = 7 -> "3 + 7 = 10"
     */
    private static String addition(int a, int b) {
        return String.format("%d + %d = %d", a, b, a + b);
    }

    /**
     * Return an expression that subtracts b from a.
     * Example: a = 3, b = 1 -> "3 - 1 = 2"
     */
    private static String subtraction(int a, int b) {
        return String.format("%d - %d = %d", a, b, a - b);
    }

    /**
     * Repeat the input string n times.
     */
    private static String repeat(String s, int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append(s);
        }
        return result.toString();
    }

    /**
     * Create a line separator using "-". Width includes the decorators if it has any.
     *
     * @param width     width of the line, which includes the decorator, if it has one
     * @param decorated if True, line starts with ">" and ends with "<", if False, consists of only "-"
     * If decorated and width is 1, return an empty string ("").
     */
    private static String line(int width, boolean decorated) {
        if (!decorated) {
            return repeat("-", width);
        } else if (decorated && width < 2) {
            return "";
        } else {
            return String.format(">%s<", repeat("-", width - 2));
        }
    }

    /**
     * Create a line separator using "-".
     *
     * @param width width of the line.
     */
    private static String line(int width) {
        return repeat("-", width);
    }

    /**
     * Center justify a string.
     *
     * "a", 3, LongerPad.LEFT -> " a "
     *
     * When the string does not fit exactly:
     * If LongerPad.LEFT make the left padding longer, otherwise the right padding should be longer.
     * "ab", 5, LongerPad.LEFT  -> "  ab "
     * "ab", 5, LongerPad.RIGHT -> " ab  "
     *
     * If the width is smaller than the length of the string, return only the center part of the text.
     * "abcde", 2, LongerPad.LEFT  -> "bc" or "cd" (both are fine)
     *
     * Return an empty string ("") if the width is negative.
     *
     * @param s string to center
     * @param width width of the outcome
     * @param pad left longer if LongerPad.LEFT, pad right longer if LongerPad.RIGHT
     * @return new center justified string
     */
    private static String center(String s, int width, LongerPad pad) {
        StringBuilder result = new StringBuilder();

        if (width < 0) {
            return "";
        }
        if (width < s.length()) {               // When result width is smaller than string length
            int delta = s.length() - width, first = delta / 2, end = s.length() - (delta - first);
            return s.substring(first, end);

        } else if (width % 2 != s.length() % 2) {   // One side has to be padded more than the other
            int delta = width - s.length(), left = 0, right = 0;

            switch (pad) {
                case LEFT:  left = delta / 2 + 1;
                            right = delta / 2;
                            break;
                default:    left = delta / 2;
                            right = delta / 2 + 1;
                            break;
            }
            result.append(repeat(" ", left)).append(s).append(repeat(" ", right));
            return result.toString();
        } else {
            int  sidePadding = (width - s.length()) / 2;

            result.append(repeat(" ", sidePadding)).append(s).append(repeat(" ", sidePadding));
            return result.toString();
        }
    }

    /**
     *System.out.println(display(2, 3, "abc", "addition", 20))
     *
     *              ABC-3bc
     * >------------------<
     * |     2 + 3 = 5    |
     * --------------------
     *  operationNumber == 0 : addition
     *  operationNumber == 1 : subtraction
     *
     *
     *
     * Create a string representing the display.
     * Use LongerPad.LEFT when centering.
     * Width of the display must be set to the assigned width or expression width, whichever is bigger.
     * If the operation is not valid, display "ERROR".
     *
     * @param a         number
     * @param b         number
     * @param name      full name of calculator company
     * @param operation operation ("addition" or "subtraction") applied to numbers a and b
     * @param width     width of the display
     * @return string representing the final format
     */
    private static String display(int a, int b, String name, String operation, int width) {
        String equation;
        StringBuilder result = new StringBuilder("");
        switch (operation) {
            case "addition":    equation = addition(a, b);
                break;
            case "subtraction": equation = subtraction(a, b);
                break;
            default:            equation = "ERROR";
                break;
        }
        if (equation.length() > width) {
            width = equation.length() + 2;
        }
        result.append(String.format("%" + width + "s\n", convertName(name)));
        result.append(line(width, true) + "\n");
        result.append("|" + center(equation, width - 2, LongerPad.LEFT) + "|\n");
        result.append(line(width));
        return result.toString();
    }

}
