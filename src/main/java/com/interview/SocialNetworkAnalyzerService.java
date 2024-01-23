package com.interview;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class SocialNetworkAnalyzerService implements SocialNetworkAnalyzer {
	private String userFile;
	private String connectionsFile;
	private Map<Integer, String> users;
	private Map<Integer, List<Integer>> friends;


	/**
	 * Initializes this service.
	 *
	 * @param usersFile       path to users file (canonical or relative)
	 * @param connectionsFile path to connections file (canonical or relative)
	 */
	public SocialNetworkAnalyzerService(String usersFile, String connectionsFile) {
		this.userFile = usersFile;
		this.connectionsFile = connectionsFile;
		this.users = new HashMap<>();
		this.friends = new HashMap<>();
		loadUsers(',');
		loadConnections(' ');
	}

	private void loadUsers(char delimiter) {
		try {
			FileLineIterator iterator = new FileLineIterator(userFile);
			while (iterator.hasNext()) {
				String str = iterator.next();
				List<String> user = StringUtils.tokenize(str, delimiter);
				users.put(Integer.valueOf(user.get(0)), user.get(1));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void loadConnections(char delimiter) {
		try {
			FileLineIterator iterator = new FileLineIterator(connectionsFile);
			while (iterator.hasNext()) {
				String str = iterator.next();
				List<String> connection = StringUtils.tokenize(str, delimiter);
				Integer user1 = Integer.valueOf(connection.get(0));
				Integer user2 = Integer.valueOf(connection.get(1));
				addConnection(user1, user2);
				addConnection(user2, user1);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void addConnection(Integer user, Integer friend) {
		List<Integer> userFriends = friends.getOrDefault(user, new ArrayList<>());
		userFriends.add(friend);
		friends.putIfAbsent(user, userFriends);
	}

	@Override
	public int numUsers() {
		return users.size();
	}

	@Override
	public int numConnections() {
		return (int) friends.values().stream().mapToLong(Collection::size).sum() / 2;
	}

	@Override
	public float averageFriendsNumber() {
		return (float) numConnections() / numUsers();
	}

	@Override
	public int idOfUser(String userName) {
		return users.entrySet().stream()
				.filter(e -> e.getValue().equals(userName))
				.map(Map.Entry::getKey)
				.findFirst().orElse(-1);
	}

	@Override
	public int numFriendOf(String userName) {
		List<Integer> userFriends = friends.getOrDefault(idOfUser(userName), new ArrayList<>());
		return userFriends.size();
	}

	@Override
	public List<String> friendsOf(String userName) {
		return friends.getOrDefault(idOfUser(userName), new ArrayList<>()).stream()
				.map(u -> users.get(u))
				.collect(Collectors.toList());
	}

	@Override
	public String mostConnectedUser() {
		int max = friends.values().stream()
				.mapToInt(List::size).max().getAsInt();
		return friends.entrySet().stream()
				.filter(e -> e.getValue().size() == max)
				.map(e -> users.get(e.getKey()))
				.findFirst()
				.orElseThrow(() -> new NoSuchElementException("No users with friends"));
	}

	@Override
	public List<String> commonFriendsOf(String user1, String user2) {
		List<Integer> friendsOfUser1 = List.copyOf(friends.getOrDefault(user1, new ArrayList<>()));
		List<Integer> friendsOfUser2 = friends.getOrDefault(user2, new ArrayList<>());
		friendsOfUser1.retainAll(friendsOfUser2);

		return friendsOfUser1.stream()
				.map(e -> users.get(e))
				.collect(Collectors.toList());
	}

	@Override
	public int histogramOf(int min, int max) {
		return (int) friends.entrySet().stream()
				.filter(e -> (e.getValue().size() >= min && e.getValue().size() <= max))
				.count();
	}

	@Override
	public List<String> secondDegreeFriends(String userName) {
		return friends.get(idOfUser(userName)).stream()
				.map(u -> friends.get(u))
				.flatMap(e -> e.stream())
				.distinct()
				.filter(e -> !friends.get(idOfUser(userName)).contains(e))
				.map(e -> users.get(e))
				.collect(Collectors.toList());
	}

	@Override
	public int degreeOfSeparation(String user, String friend) {
		Integer userId = idOfUser(user);
		Integer friendId = idOfUser(friend);

		if (!users.containsKey(userId) || !users.containsKey(friendId))
			return -1;

		Queue<Integer> queue = new LinkedList<>();
		Set<Integer> checked = new HashSet<>();
		Map<Integer, Integer> degrees = new HashMap<>();

		queue.add(userId);
		checked.add(userId);
		degrees.put(userId, 0);

		while (!queue.isEmpty()) {
			Integer currentUserId = queue.poll();

			for (Integer currentFriendId : friends.get(currentUserId)) {
				if (!checked.contains(currentFriendId)) {
					queue.add(currentFriendId);
					checked.add(currentFriendId);
					degrees.put(currentFriendId, degrees.get(currentUserId) + 1);

					if (currentFriendId == friendId) {
						return degrees.get(friendId);
					}
				}
			}
		}

		return -1;
	}

}




