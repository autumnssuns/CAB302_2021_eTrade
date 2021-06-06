package common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {
    /* Hash Password method */

    /**
     * Turn input password into a hashed string
     * @param password user password string
     * @return hashed password
     */
    public static String HashPassword(String password){

        String Hashedpassword = null;
        try {

            //Create message digest instance
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            //Add password bytes to digest
            messageDigest.update(password.getBytes());
            //Get the hash password's bytes
            byte[] ByteResult = messageDigest.digest(password.getBytes());
            //Convert it to string
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< ByteResult.length ;i++)
            {
                sb.append(Integer.toString((ByteResult[i] & 0xff) + 0x100, 16).substring(1));
            }

            Hashedpassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Hashedpassword;
    }


/** thinking about salt...
 *
 */
//    private static byte[] getSalt() throws NoSuchAlgorithmException
//    {
//        //Always use a SecureRandom generator
//        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//        //Create array for salt
//        byte[] salt = new byte[16];
//        //Get a random salt
//        sr.nextBytes(salt);
//        //return salt
//        return salt;
//    }

}
