public enum ElementID {
    SIGN_IN (ElementType.BUTTON),
    SIGN_UP (ElementType.BUTTON),
    CREATE_ACCOUNT (ElementType.BUTTON),
    BACK_SIGN_IN (ElementType.BUTTON),
    SIGN_OUT (ElementType.BUTTON),
    ADD_TO_BET (ElementType.BUTTON),
    REMOVE_FROM_BET (ElementType.BUTTON),
    CHOOSE_HEADS (ElementType.BUTTON),
    CHOOSE_TAILS (ElementType.BUTTON),
    FLIP (ElementType.BUTTON),
    
    NAME (ElementType.TEXTFIELD),
    USERNAME (ElementType.TEXTFIELD),
    PASSWORD (ElementType.TEXTFIELD),
    CONFIRM_PASSWORD (ElementType.TEXTFIELD),
    BET_AMOUNT (ElementType.TEXTFIELD);


    private final ElementType type;

    private ElementID(ElementType type) {
        this.type = type;
    }

    public ElementType getType() {
        return type;
    }
}