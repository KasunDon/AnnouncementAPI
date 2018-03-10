package co.tide.announcementapi.domain.entity;

public enum Rating {

    LIKE,
    DISLIKE;

    private static final String LIKE_VALUE = "LIKE";
    private static final String DISLIKE_VALUE = "DISLIKE";

    public static Rating of(
        String value
    ) {
        switch (value) {

            case LIKE_VALUE:
                return LIKE;

            case DISLIKE_VALUE:
                return DISLIKE;

            default:
                throw new IllegalArgumentException("Rating value is incorrect, must be [LIKE|DISLIKE].");
        }

    }

}
