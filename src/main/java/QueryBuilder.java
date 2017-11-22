public class QueryBuilder {

    public static void main (String[] args) {
        String test = "MATCH (user:User)\n" +
                "WHERE user.Id = 1234\n" +
                "RETURN user";
        System.out.println(deleteReturn(test));
    }

    public static void TreeBuilder(String query) {
        String queryPrepared = deleteReturn(query);

    }

    private static String deleteReturn(String query) {
        query = query.trim().replaceAll("\n", " ");
        String cleaned[] = query.split("WHERE");
        return cleaned[0];
    }
}
