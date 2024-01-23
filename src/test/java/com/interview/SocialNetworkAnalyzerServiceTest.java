package com.interview;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SocialNetworkAnalyzerServiceTest {
    private static SocialNetworkAnalyzerService analyzerService;

    @BeforeAll
    static void setUp()  {
        String mypath = "src/main/java/com/interview/";
        analyzerService = new SocialNetworkAnalyzerService(mypath + "users.csv", mypath + "connections.txt");
    }

    @Test
    void testNumUsers() {
        assertEquals(333, analyzerService.numUsers());
    }

    @Test
    void testNumConnections() {
        assertEquals(2519, analyzerService.numConnections());
    }

    @Test
    void testMostConnected() {
        String mostConnectedUser = analyzerService.mostConnectedUser();
        assertEquals(56,analyzerService.idOfUser(mostConnectedUser) );
    }
    @Test
    void testCommonFriendsOf() {
//        List<String> result = analyzerService.commonFriendsOf("Milton Swanson", "Kari Ames");
    //    System.out.println(result);
    }
    @Test
    void testAverageNumConnections() {
        double result = analyzerService.averageFriendsNumber();
        assertEquals(7.56, result, 0.01);
    }
    @Test
    void testfriendsOf(){
        List<String> result= analyzerService.friendsOf("Kari Ames");
        System.out.println(result);

        List<String> expected = Arrays.asList("Kari Ames","Dixie Rogers","Beth Booker");
       // assertEquals(expected, result);
    }
    @Test
    void testhistogramOf(){
        assertEquals(analyzerService.histogramOf(10,20),99);
        assertEquals(analyzerService.histogramOf(21,30),32);
        assertEquals(analyzerService.histogramOf(31,40),15);
        assertEquals(analyzerService.histogramOf(41,50),11);
        assertEquals(analyzerService.histogramOf(51,60),6);
    }

    @Test
    void testdegreeOfSeparation (){
        assertEquals(1,analyzerService.degreeOfSeparation("Nicholas Steele", "Jennie Story"));
        assertEquals(2,analyzerService.degreeOfSeparation("Jeremiah Calderon", "Kirsten Donahue"));
        assertEquals(2,analyzerService.degreeOfSeparation("Gloria Hopkins", "Kirsten Donahue"));
    }
    @Test
    void printOut() {

//        System.out.println("commonFriendsOf="+analyzerService.commonFriendsOf("Rebbecca Sexton","Andres Berger"));

        System.out.println("analyzerService.averageFriendsNumber()="+analyzerService.averageFriendsNumber());


    }




}
