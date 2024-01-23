package com.interview;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SocialNetworkAnalyzerServiceV3 implements SocialNetworkAnalyzer {

    private Map<Integer, String> usersMap = new HashMap<>();
    private Map<Integer, Set<Integer>> connectionsMap = new HashMap<>();
    /**
     * Initializes this service.
     *
     * @param usersFile path to users file (canonical or relative)
     * @param connectionsFile path to connections file (canonical or relative)
     */
    public SocialNetworkAnalyzerServiceV3(String usersFile, String connectionsFile) throws FileNotFoundException {
        System.out.println("Process started for files="+usersFile+" and "+connectionsFile);
        FileLineIterator userFileIterator = new FileLineIterator(usersFile);
        while (userFileIterator.hasNext()){
            List<String> userDetails = StringUtils.tokenize(userFileIterator.next(),',');
            usersMap.put(Integer.valueOf(userDetails.get(0)), userDetails.get(1));
        }
        System.out.println("User's file processed, "+usersMap.size()+" loaded");

        FileLineIterator connectionsFileIterator = new FileLineIterator(connectionsFile);
        while (connectionsFileIterator.hasNext()){
            List<String> connections = StringUtils.tokenize(connectionsFileIterator.next(), ' ');
            Integer user1 = Integer.valueOf(connections.get(0));
            Integer user2 = Integer.valueOf(connections.get(1));
            connectionsMap.computeIfAbsent(user1, k -> new HashSet<>()).add(user2);
            connectionsMap.computeIfAbsent(user2, k -> new HashSet<>()).add(user1);
        }
        System.out.println("Connection's file processed, connections for "+connectionsMap.size()+" users loaded");
        System.out.println("Overall connections qty = "+connectionsMap.values()
                .stream()
                .mapToInt(Set::size)
                .sum() ) ;
    }


    @Override
    public int numUsers() {
        //Add your implementation
        return usersMap.size();
    }

    @Override
    public int numConnections() {
        //Add your implementation
        return connectionsMap.values()
                .stream()
                .mapToInt(Set::size)
                .sum() / 2 ;
    }

    @Override
    public float averageFriendsNumber() {
        //Add your implementation
       return  (float) connectionsMap.values()
                .stream()
                .mapToInt(Set::size)
                .average()
                .orElse(0.0);
    }

    @Override
    public int idOfUser(String userName) {
        //Add your implementation
        return usersMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(userName))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
    }

    @Override
    public int numFriendOf(String userName) {
        //Add your implementation
        return connectionsMap.get(idOfUser(userName))
                .size();
    }

    @Override
    public List<String> friendsOf(String userName) {
        //Add your implementation
        return connectionsMap.getOrDefault(idOfUser(userName), new HashSet<>()).stream()
                .map(id -> usersMap.get(id))
                .collect(Collectors.toList());
    }

    @Override
    public String mostConnectedUser() {
        int max = connectionsMap.values().stream()
                .mapToInt(Set::size).max().getAsInt();
        return connectionsMap.entrySet().stream()
                .filter(e -> e.getValue().size() == max)
                .map(e -> usersMap.get(e.getKey()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No users with friends found"));
    }

    @Override
    public List<String> commonFriendsOf(String user1, String user2) {
        Integer userId1 = idOfUser(user1);
        Integer userId2 = idOfUser(user2);
        Set<Integer> result = new HashSet<>(connectionsMap.getOrDefault(userId1, Set.of()));
        result.retainAll(connectionsMap.getOrDefault(userId2, new HashSet<>()));
        return result.stream().map(key -> usersMap.get(key)).collect(Collectors.toList());
    }

    @Override
    public int histogramOf(int min, int max) {
        return (int)connectionsMap.entrySet().stream()
                .filter(e->(e.getValue().size()>=min && e.getValue().size()<=max))
                .count();
    }

    @Override
    public List<String> secondDegreeFriends(String userName) {
         return connectionsMap.get(idOfUser(userName)).stream()
                 .map(u -> connectionsMap.get(u))
                 .flatMap(e -> e.stream())
                 .distinct()
                 .filter(e ->!connectionsMap.get(idOfUser(userName)).contains(e))
                 .map(u ->usersMap.get(u))
                 .collect(Collectors.toList());
    }

    @Override
    public int degreeOfSeparation(String user1, String user2) {
        Integer userId = idOfUser(user1);
        Integer friendId = idOfUser(user2);

        if (!usersMap.containsKey(userId) || !usersMap.containsKey(friendId))
            return -1;

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> checked = new HashSet<>();
        Map<Integer, Integer> degrees = new HashMap<>();

        queue.add(userId);
        checked.add(userId);
        degrees.put(userId, 0);

        while (!queue.isEmpty()) {
            Integer currentUserId = queue.poll();

            connectionsMap.getOrDefault(currentUserId, Collections.emptySet())
                    .stream()
                    .filter(currentFriendId -> !checked.contains(currentFriendId))
                    .forEach(currentFriendId -> {
                        queue.add(currentFriendId);
                        checked.add(currentFriendId);
                        degrees.put(currentFriendId, degrees.get(currentUserId) + 1);

                        if (currentFriendId.equals(friendId)) {
                            degrees.put(friendId, degrees.get(currentFriendId));
                        }
                    });
        }

        return degrees.getOrDefault(friendId, -1);
    }

}

