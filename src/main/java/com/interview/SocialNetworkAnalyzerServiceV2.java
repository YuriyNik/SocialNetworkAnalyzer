package com.interview;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SocialNetworkAnalyzerServiceV2 implements SocialNetworkAnalyzer {
    private final Map<Integer,User> usersMap = new HashMap<>();
    public User getUserById( Integer targetUserId) {
        return usersMap.get(targetUserId);
    }

    public User getUserByName( String userName) {
        return usersMap.values()
                .stream()
                .filter(user -> user.getName().equals(userName))
                .findFirst()
                .orElse(null);
    }

    public User getMostConnected() {
        return usersMap.values()
                .stream()
                .max(Comparator.comparingInt(user -> user.getFriends().size()))
                .orElse(null);
    }

    /**
     * Initializes this service.
     *
     * @param usersFile path to users file (canonical or relative)
     * @param connectionsFile path to connections file (canonical or relative)
     */
    public SocialNetworkAnalyzerServiceV2(String usersFile, String connectionsFile) {

        try (Stream<String> lines = Files.lines(Paths.get(usersFile))) {
            lines.map(line -> StringUtils.tokenize(line, ','))
                    .forEach(userTokens -> {
                        User userToLoad = new User(Integer.parseInt(userTokens.get(0)), userTokens.get(1));
                        usersMap.put(Integer.valueOf(userTokens.get(0)), userToLoad);
                    });
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        try (Stream<String> connections = Files.lines(Paths.get(connectionsFile))) {
            connections.map(connection -> StringUtils.tokenize(connection, ' '))
                    .forEach(connectionTokens ->{
                        getUserById(Integer.valueOf(connectionTokens.get(0))).addFriends(Integer.valueOf(connectionTokens.get(1)));
                        getUserById(Integer.valueOf(connectionTokens.get(1))).addFriends(Integer.valueOf(connectionTokens.get(0)));
                    });
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        System.out.println("users loaded=="+usersMap.size());
    }

    public static void main(String[] args)  {
        String mypath = "src/main/java/com/interview/";
        SocialNetworkAnalyzerServiceV2 analyzerService =
                new SocialNetworkAnalyzerServiceV2(mypath+"users.csv", mypath+"connections.txt");
        System.out.println("analyzerService.getUserById(346,Jessie Duncan)="+analyzerService.getUserById(346));
        System.out.println("analyzerService.getUserByName(347,Lynette Webster)="+analyzerService.getUserByName("Lynette Webster"));
        System.out.println("numUsers="+analyzerService.numUsers());
        System.out.println("numConnections="+analyzerService.numConnections());
        System.out.println("averageFriendsNumber="+analyzerService.averageFriendsNumber());
        System.out.println("idOfUser(Rebbecca Sexton)="+analyzerService.idOfUser("Rebbecca Sexton"));
        System.out.println("mostConnectedUser="+analyzerService.mostConnectedUser());
        System.out.println("friendsOf="+analyzerService.friendsOf("Rebbecca Sexton"));
        System.out.println("commonFriendsOf="+analyzerService.commonFriendsOf("Rebbecca Sexton","Andres Berger"));
        System.out.println("secondDegreeFriends="+analyzerService.secondDegreeFriends("Marion Sanford"));
        System.out.println("histogramOf{10,20}="+analyzerService.histogramOf(10,20));
        System.out.println("histogramOf{21,30}="+analyzerService.histogramOf(21,30));
        System.out.println("histogramOf{31,40}="+analyzerService.histogramOf(31,40));
        System.out.println("histogramOf{41,50}="+analyzerService.histogramOf(41,50));
        System.out.println("histogramOf{51,60}="+analyzerService.histogramOf(51,60));
        System.out.println("degreeOfSeparation="+analyzerService.degreeOfSeparation("Rebbecca Sexton","Andres Berger"));
        System.out.println("degreeOfSeparation="+analyzerService.degreeOfSeparation("Marion Sanford","Sandy Carlson"));
        System.out.println("degreeOfSeparation="+analyzerService.degreeOfSeparation("Marion Sanford",analyzerService.getUserById(280).getName()));
        for (User user:analyzerService.usersMap.values()){
            System.out.println(user);
        }
    }

    @Override
    public int numUsers() {
        return usersMap.size();
    }
    @Override
    public int numConnections() {
        return usersMap.values()
                .stream()
                .mapToInt(user -> user.getFriends().size())
                .sum();
    }
    @Override
    public float averageFriendsNumber() {
        return usersMap.isEmpty() ? 0 : (float) numConnections() /usersMap.size();
    }

    @Override
    public int idOfUser(String userName) {
        return getUserByName(userName).getId();
    }

    @Override
    public int numFriendOf(String userName) {
        return getUserByName(userName).getFriends().size();
    }

    @Override
    public List<String> friendsOf(String userName) {
        List<String> friendsNames = new ArrayList<>();
        Set<Integer> friends = getUserByName(userName).getFriends();
        for (Integer friend:friends){
            friendsNames.add(getUserById(friend).getName());
        }
        return friendsNames;
    }

    @Override
    public String mostConnectedUser() {
        return getMostConnected().getName();
    }

    @Override
    public List<String> commonFriendsOf(String user1, String user2) {
        Set<Integer> user1Fr = getUserByName(user1).getFriends();
        Set<Integer> user2Fr = getUserByName(user2).getFriends();
        if (user1Fr == null || user2Fr == null) {
            return null;
        }
        return user1Fr.stream()
                .filter(user2Fr::contains)
                .map(this::getUserById)
                .map(User::getName)
                .collect(Collectors.toList());
    }

    @Override
    public int histogramOf(int min, int max) {
        List<Integer> friendsList = new ArrayList<>();

        for (User user:usersMap.values()){

            friendsList.add(user.getFriends().size());
        }

        int[] data = new int[friendsList.size()];

        for (int i = 0; i < friendsList.size(); i++) {
            data[i] = friendsList.get(i);
        }


        Map<Integer, Integer> histogram = new HashMap<>();

        for (int value : data) {
            if (value >= min && value <= max) {
                histogram.put(value, histogram.getOrDefault(value, 0) + 1);
            }
        }


        int totalCount = 0;
        for (int i = min; i <= max; i++) {
            totalCount += histogram.getOrDefault(i, 0);
        }

        return totalCount;
    }

    @Override
    public List<String> secondDegreeFriends(String userName) {
        User usrFriend = getUserByName(userName);
        System.out.println("secondDegreeFriends for "+usrFriend.getId()+";"+usrFriend.getName());
        Set<Integer> secondDegreeFriends = new HashSet<>();
        List<User> friends = new ArrayList<>();
        for (Integer friendid:usrFriend.getFriends())
        {
            friends.add(getUserById(friendid));
        }
        for (User friend : friends) {
            secondDegreeFriends.addAll(friend.getFriends());
        }

        //remove original friends of the userName
        usrFriend.getFriends().forEach(secondDegreeFriends::remove);
        System.out.println("secondDegreeFriends.size="+secondDegreeFriends.size());

        List<String> forResult = new ArrayList<>();
        System.out.println();
        for (Integer secondDF:secondDegreeFriends){
            System.out.print("{"+secondDF+"}");
            forResult.add(getUserById(secondDF).getName());
        }

        return forResult;
    }

    @Override
    public int degreeOfSeparation(String userName1, String userName2) {
        User user1 = getUserByName(userName1);
        User user2 = getUserByName(userName2);
        if (user1.equals(user2)) {
            return 0;
        }
        // Используем алгоритм поиска в ширину для определения удаленности
        Queue<User> queue = new LinkedList<>();
        Set<User> visited = new HashSet<>();

        queue.add(user1);
        visited.add(user1);

        int distance = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                User current = queue.poll();

                assert current != null;
                for (Integer friendId : current.getFriends()) {
                    User friend = getUserById(friendId);

                    if (!visited.contains(friend)) {
                        if (friend.equals(user2)) {
                            return distance + 1; // Найдены друзья, возвращаем удаленность
                        }

                        queue.add(friend);
                        visited.add(friend);
                    }
                }
            }

            distance++;
        }

        return -1;
    }
    public Map<Integer, User> getUsersMap() {
        return usersMap;
    }
    public void addUsertoUsersMap(User user){
        usersMap.put(user.getId(),user);
    }

}

