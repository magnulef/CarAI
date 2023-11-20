package utils.json;

import java.util.ArrayList;
import java.util.List;

public abstract class PathToken {

    private PathToken previous = null;
    private PathToken next = null;

    public static List<PathToken> parse(String directJsonPath) {
        List<PathToken> result = new ArrayList<>();
        StringBuilder text = new StringBuilder(0);
        boolean inArray = false;
        char lastChar = ' ';
        PathToken lastToken = null;
        for (int i = 0, n = directJsonPath.length(); i < n; i++) {
            try {
                char c = directJsonPath.charAt(i);
                if (c == '$') {
                    lastChar = c;
                    continue;
                }
                if (c == '[') {
                    if (lastChar != ']') {
                        PathToken lastToken1 = lastToken;
                        lastToken = addPathToken(result, text, lastToken1);
                    } else {
                        // ignore it
                    }
                    inArray = true;
                } else if (inArray) {
                    if (c == ']') {
                        PathToken token = new ArrayToken(text.toString());
                        result.add(token);
                        text.setLength(0);
                        if (lastToken != null) {
                            lastToken.next = token;
                            token.previous = lastToken;
                        }
                        lastToken = token;
                        inArray = false;
                    } else {
                        text.append(c);
                    }
                } else if (c == ']') {
                    throw new Exception("Closing bracket found without opening before");
                } else if (c == '.') {
                    if (lastChar != ']' && lastChar != '$') {
                        // we expect a normal attribute name now
                        PathToken token = new AttributeToken(text.toString());
                        result.add(token);
                        text.setLength(0);
                        if (lastToken != null) {
                            lastToken.next = token;
                            token.previous = lastToken;
                        }
                        lastToken = token;
                    } else if (i == n - 1) {
                        // we have a dot at the end and this is not allowed
                        throw new Exception("Dot-delimiter to a next attribute found but we are at the end");
                    } else {
                        // ignore it
                    }
                } else {
                    text.append(c);
                }
                lastChar = c;
            } catch (Exception e) {
                throw new RuntimeException( "Parsing failed at pos: " + i, e);
            }
        }
        addPathToken(result, text, lastToken);
        return result;
    }

    private static PathToken addPathToken(List<PathToken> result, StringBuilder text, PathToken lastToken1) {
        if (text.length() > 0) {
            // we expect a normal attribute here
            PathToken token = new AttributeToken(text.toString());
            result.add(token);
            text.setLength(0);
            if (lastToken1 != null) {
                lastToken1.next = token;
                token.previous = lastToken1;
            }
            return token;
        }
        return null;
    }

    public String getPath() {
        StringBuilder path = new StringBuilder();
        buildPath(path);
        return path.toString();
    }

    private void buildPath(StringBuilder path) {
        if (previous != null) {
            previous.buildPath(path);
        }
        if (this instanceof AttributeToken) {
            String name = toString();
            if (path.length() > 0) {
                path.append(".");
            }
            path.append(name);
        } else if (this instanceof ArrayToken) {
            path.append(toString());
        }
    }

    public PathToken getPrevious() {
        return previous;
    }

    public PathToken getNext() {
        return next;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean isNextTokenArray() {
        return (next instanceof ArrayToken);
    }

}