package codieval.complexity;

import java.util.StringTokenizer;

public class Complexity {
    public static BigO getBigO(String code) {
        BigO bigO = new BigO();

        int open_bracket=0;
        Lexer lexer = new Lexer(code, new char[] {' ', '\t', '(', ')', '{', '}', ';'});

        for(int i = 0; i < lexer.size(); i++)
        {
            String token = lexer.get(i).toString();

            if(token.equals("for")) {
                int[] analysis = depthOfForLoop(lexer, i);
                int depth = analysis[0];
                i = analysis[1];
                bigO.add(depth);
            }
        }

        return bigO;
    }

    public static int[] depthOfForLoop(Lexer lexer, int i) {
        int depth = 1;

        i = parseParentheses(lexer, i);

        i++;
        int curlyBraces = 0;

        if(i < lexer.size() && lexer.get(i).toString().equals("{")) {
            curlyBraces++;
            i++;

            for(; i < lexer.size(); i++) {
                String token = lexer.get(i).toString();

                if(token.equals("{")) {
                    curlyBraces++;
                } else if(token.equals("}")) {
                    curlyBraces--;
                }
                else if(token.equals("for")) {
                    int[] analysis = depthOfForLoop(lexer, i);
                    int depth2 = analysis[0];
                    i = analysis[1];

                    if(depth2 + 1 > depth) {
                        depth = depth2 + 1;
                    }
                }

                if(curlyBraces == 0) {
                    break;
                }
            }
            i++;
        } else {
            for(; i < lexer.size(); i++) {
                String token = lexer.get(i).toString();

                if(token.equals(";")) {
                    break;
                } else if(token.equals("for")) {
                    int[] analysis = depthOfForLoop(lexer, i);
                    int depth2 = analysis[0];
                    i = analysis[1];

                    depth = depth2 + 1;

                    break;
                }
            }
        }
        return new int[] {depth, i};
    }

    public static int parseParentheses(Lexer lexer, int i) {
        int parentheses = 0;

        i += 2;
        parentheses++;

        for(; i < lexer.size(); i++) {
            String token = lexer.get(i).toString();
            if(token.equals("(")) {
                parentheses++;
            } else if(token.equals(")")) {
                parentheses--;
            }

            if(parentheses == 0) {
                break;
            }
        }

        return i;
    }
}
