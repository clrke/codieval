package codieval.complexity;

import java.util.StringTokenizer;

public class Complexity {
    public static BigO getBigO(String code) {
        BigO bigO = new BigO();

        int open_bracket=0;
        StringTokenizer t = new StringTokenizer(code);

        String token="";
        int current = 0;

        while(t.hasMoreTokens())
        {
            token = t.nextToken();

            if(token.equals("{")) open_bracket++;

            if(token.equals("}")) open_bracket--;

            if(token.length()>=3) if(token.substring(0, 3).equals("for"))  current++;

            if(open_bracket==0&&token.equals("}"))
            {
                bigO.add(current);
                current = 0;
            }
        }

        return bigO;
    }
}
