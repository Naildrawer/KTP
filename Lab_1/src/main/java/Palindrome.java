public class Palindrome {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            if (isPalindrome(args[i])) {
                System.out.println("Палиндром " + s);
            }
            else
                System.out.println("Не палиндроме " + s);
        }

    }

    public static String reverseString(String s){
        String r = "";
        for (int l = s.length() - 1; l >= 0; --l) {
            r += s.charAt(l);
        }
        return r;
    }

    public static Boolean isPalindrome (String s){
        if (s.equals(reverseString(s))) {
            return true;

        }
        return false;

    }

}
