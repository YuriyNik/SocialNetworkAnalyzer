package com.interview;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocialNetworkAnalyzerServiceV3Test {

    private static SocialNetworkAnalyzerServiceV3 analyzerService;

    @BeforeAll
    static void setUp() throws FileNotFoundException {
        String mypath = "src/main/java/com/interview/";
        analyzerService = new SocialNetworkAnalyzerServiceV3(mypath + "users.csv", mypath + "connections.txt");
    }

    @Test
    void testdegreeOfSeparation (){
        assertEquals(1,analyzerService.degreeOfSeparation("Nicholas Steele", "Jennie Story"));
        assertEquals(2,analyzerService.degreeOfSeparation("Jeremiah Calderon", "Kirsten Donahue"));
        assertEquals(2,analyzerService.degreeOfSeparation("Gloria Hopkins", "Kirsten Donahue"));
    }

    @Test
    void printout(){
        System.out.println("printout test");
        System.out.println("numUsers = "+analyzerService.numUsers());
        System.out.println("numConnections = "+analyzerService.numConnections());
        System.out.println("averageFriendsNumber = "+analyzerService.averageFriendsNumber());
        System.out.println("idOfUser Ana Goodrich = "+analyzerService.idOfUser("Ana Goodrich"));
        System.out.println("numFriendOf Ana Goodrich = "+analyzerService.numFriendOf("Ana Goodrich"));
        System.out.println("friendsOf Ana Goodrich = "+analyzerService.friendsOf("Ana Goodrich"));
        System.out.println("mostConnectedUser = "+analyzerService.mostConnectedUser() + ";Qty of friends="+ analyzerService.numFriendOf(analyzerService.mostConnectedUser()));
        System.out.println("friendsOf(\"Rebbecca Sexton\",\"Andres Berger\")="+analyzerService.commonFriendsOf("Rebbecca Sexton","Andres Berger"));
        System.out.println("histogramOf(0,10) = "+analyzerService.histogramOf(0,10));
        System.out.println("histogramOf(11,20) = "+analyzerService.histogramOf(11,20));
        System.out.println("histogramOf(21,30) = "+analyzerService.histogramOf(21,30));
        System.out.println("secondDegreeFriends Ana Goodrich = "+analyzerService.secondDegreeFriends("Ana Goodrich"));
    }

}
