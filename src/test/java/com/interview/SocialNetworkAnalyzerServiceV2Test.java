package com.interview;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SocialNetworkAnalyzerServiceV2Test {
    private static SocialNetworkAnalyzerServiceV2 analyzerService;

    @BeforeAll
    static void setUp()  {
            String mypath = "src/main/java/com/interview/";
            analyzerService = new SocialNetworkAnalyzerServiceV2(mypath + "users.csv", mypath + "connections.txt");
    }

    @Test
    void testGetUserById() {
        User user = analyzerService.getUserById(346);
        assertNotNull(user);
        assertEquals(346, user.getId());
        assertEquals("Jessie Duncan", user.getName());
    }

    @Test
    void testGetUserByName() {
        User user = analyzerService.getUserByName("Lynette Webster");
        assertNotNull(user);
        assertEquals(347, user.getId());
        assertEquals("Lynette Webster", user.getName());
    }

    @Test
    void testNumUsers() {
        assertEquals(333, analyzerService.numUsers());
    }

    @Test
    void testNumConnections() {
        assertEquals(5038, analyzerService.numConnections());
    }

    @Test
    void testMostConnected() {
        assertEquals(analyzerService.getUserById(56), analyzerService.getMostConnected());
    }
    @Test
    void testCommonFriendsOf() {
        User user1 = new User(500,"user1");
        user1.addFriends(1);
        user1.addFriends(2);
        user1.addFriends(3);
        analyzerService.addUsertoUsersMap(user1);
        User user2 = new User(501, "user2");
        user2.addFriends(2);
        user2.addFriends(3);
        user2.addFriends(4);
        analyzerService.addUsertoUsersMap(user2);
        List<String> result = analyzerService.commonFriendsOf("user1", "user2");
        List<String> expected = Arrays.asList("Kari Ames", "Dixie Rogers");
        assertEquals(expected, result);
    }
    @Test
    void testAverageNumConnections() {
        double result = analyzerService.averageFriendsNumber();
        assertEquals(15.13, result, 0.01);
    }
     @Test
     void testfriendsOf(){
         User user = new User(501, "userF");
         user.addFriends(2);
         user.addFriends(3);
         user.addFriends(4);
         analyzerService.addUsertoUsersMap(user);
         List<String> result= analyzerService.friendsOf("userF");
         List<String> expected = Arrays.asList("Kari Ames","Dixie Rogers","Beth Booker");
         assertEquals(expected, result);
     }

    @Test
    void testdegreeOfSeparation (){
        assertEquals(1,analyzerService.degreeOfSeparation("Nicholas Steele", "Jennie Story"));
        assertEquals(2,analyzerService.degreeOfSeparation("Jeremiah Calderon", "Kirsten Donahue"));
        assertEquals(2,analyzerService.degreeOfSeparation("Gloria Hopkins", "Kirsten Donahue"));
    }
    @Test
    void printOut() {

        System.out.println("commonFriendsOf="+analyzerService.commonFriendsOf("Rebbecca Sexton","Andres Berger"));

        System.out.println("analyzerService.averageFriendsNumber()="+analyzerService.averageFriendsNumber());

        System.out.println("mostConnectedUser = "+analyzerService.mostConnectedUser() + ";Qty of friends="+ analyzerService.numFriendOf(analyzerService.mostConnectedUser()));
        System.out.println("friendsOf(\"Milton Swanson\", \"Kari Ames\")="+analyzerService.commonFriendsOf("Milton Swanson", "Kari Ames"));


        User user = analyzerService.getUserByName("Jessie Duncan");
        assertNotNull(user);
        assertEquals(346, user.getId());
        assertEquals("Jessie Duncan", user.getName());

        user = analyzerService.getUserByName("Lynette Webster");
        assertNotNull(user);
        assertEquals(347, user.getId());
        assertEquals("Lynette Webster", user.getName());

        user = analyzerService.getUserByName("Jeannie Brennan");
        assertNotNull(user);
        assertEquals(167, user.getId());
        assertEquals("Jeannie Brennan", user.getName());

    }




}

