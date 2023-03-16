package org.ltejeda.springredditclone.exceptions;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String message) {
        super(message);
    }

    public SpringRedditException(String message, Exception exception){
        super(message);
    }
}
