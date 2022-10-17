package com.example.jwt_demo1.payload;

public class ResponseMessageFile{
        private String message;

        public ResponseMessageFile(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        
}
