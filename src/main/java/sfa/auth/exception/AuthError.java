package sfa.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class AuthError extends RuntimeException {
    public ResponseEntity<String> AuthError (){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
    }

}
