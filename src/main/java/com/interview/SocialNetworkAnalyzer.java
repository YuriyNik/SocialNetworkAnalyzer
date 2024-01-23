package com.interview;


import java.util.List;

public interface SocialNetworkAnalyzer {

	/**
	 * Counts the number of users in the network
	 * 
	 * @return the number of users in the network
	 */
	public int numUsers();
	
	/**
	 * Counts the number of connections in the network
	 * 
	 * @return the number of connections in the network
	 */
	public int numConnections();

	/**
	 * Find the Id of user with provided name and returns it
	 * 
	 * @param userName the user name
	 * 
	 * @return the Id of user with provided name
	 */
	public int idOfUser(String userName);

	/**
	 * Calculates the list of friends (names) of a user.
	 * List should be empty in case user have no friends.
	 * 
	 * @param userName the user name
	 * 
	 * @return a list containing all the friends names of a user (empty if none exist)
	 */
	public List<String> friendsOf(String userName);
	
	/**
	 * Calculates the number of friends (names) of a user
	 * 
	 * @param userName the user name
	 * 
	 * @return the number of friends of the user
	 */
	public int numFriendOf(String userName);
	
	/**
	 * Calculate the average number of friends per user in the network
	 * 
	 * @return the average number of friends per user in the network
	 */
	public float averageFriendsNumber();	
	
	/**
	 * Calculates the name of the most connected user in the network (with highest number of friends)
	 * 
	 * @return the most connected user name
	 */
	public String mostConnectedUser();
	
	/**
	 * Find the common friends (names) of two users.
	 * 
	 * @param user1 the name of one user
	 * @param user2 the name of second user
	 * 
	 * @return list of common friends names or empty if none exist
	 */
	public List<String> commonFriendsOf(String user1, String user2);

	/**
	 * Finds the number of users which have number of friends between
	 * provided range, inclusive.
	 * 
	 * @param min lower bound for number of users
	 * @param max upper bound for number of users
	 * 
	 * @return number of users which have number of friends between provided range, inclusive.
	 */
	public int histogramOf(int min, int max);
	
	/**
	 * Finds the second degree friends (names) for a provided user name. 
	 * Second degree friends are friends of friends which are not direct friends.
	 * 
	 * @param userName the user name
	 * 
	 * @return list of second degree friends names
	 */
	public List<String> secondDegreeFriends(String userName);	

	/**
	 * Find the degree of separation between two users.
	 * 
	 * @param user1 the name of one user
	 * @param user2 the name of second user
	 * 
	 * @return the degree of separation of provided users or -1 if users are not connected.
	 */
	public int degreeOfSeparation(String user1, String user2);
	
}
