package com.biglobby.server.chat.enumtypes;

public enum MessageType {
    TEXT {
        @Override
        public String messageType() {
            return "TEXT";
        }
    },
    IMAGE {
        @Override
        public String messageType() {
            return "IMAGE";
        }
    },
    LINK {
        @Override
        public String messageType() {
            return "LINK";
        }
    },
    FILE {
        @Override
        public String messageType() {
            return "FILE";
        }
    },
    UNDEFINED {
        @Override
        public String messageType() {
            return "UNDEFINED";
        }
    };

    public String messageType() {
        return "UNDEFINED";
    }

    public static MessageType parseToMessageType(String value) {
        switch (value) {
            case "TEXT":
                return TEXT;
            case "IMAGE":
                return IMAGE;
            case "FILE":
                return FILE;
            case "LINK": 
                return LINK;
            default:
                return UNDEFINED;
        }
    }

}
