import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    static List<String> quotes;
    static Random random;
    static ExecutorService executorService;

    public static void main(String[] args) {
        executorService = Executors.newFixedThreadPool(4);

        random = new Random();
        quotes = new ArrayList<>(Arrays.asList(
                "\"Anybody want a peanut?\" - Fezzik (Quote from Princess Bride)",
                "\"Inconceivable!\" - Vizzini (Quote from Princess Bride)",
                "\"I only dog paddle.\" - Fezzik (Quote from Princess Bride)",
                "\"I wonder if he's using the same wind we are using?\" - Montoya (Quote from Princess Bride)",
                "\"You keep using that word. I do not think it means what you think it means.\" - Montoya (Quote from Princess Bride)",
                "\"People in masks cannot be trusted.\" - Fezzik (Quote from Princess Bride)",
                "\"What, Humperdinck? Humperdinck! Humperdinck! Humperdinck! Humperdinck! Humperdinck!\" - Valerie (Quote from Princess Bride)",
                "\"Chocolate coating makes it go down easier.\" - Valerie (Quote from Princess Bride)",
                "\"Have fun storming the castle.\" - Miracle Max (Quote from Princess Bride)",
                "\"I've always been a quick healer.\" - Westley (Quote from Princess Bride)",
                "\"Don't pester him, he's had a hard day.\" - Fezzik (Quote from Princess Bride)",
                "\"Doth mother know you weareth her drapes?\" - Tony Stark (Iron Man),",
                "\"If we can't protect the Earth, you can be damned well sure we'll avenge it!\" - Tony Stark (Iron Man)",
                "\"Sure it was. That's extortion, that's the word. What kind do you want? Great minds think alike. Juice pops exactly was on my mind.\" - Tony Stark (Iron Man)",
                "\"They say that the best weapon is the one you never have to fire. I respectfully disagree. I prefer the weapon you only have to fire once.\" - Tony Stark (Iron Man)",
                "\"Don't say \"wind farm.\" I'm already feeling gassy.\" - Tony Stark (Iron Man)",
                "\"Are you seriously telling me that your plan to save the universe is based on \"Back to the Future?\" - Tony Stark (Iron Man)",
                "\"You look like you have friends in low places.\" - Tony Stark (Iron Man)",
                "\"Hey, you said one out of fourteen million, we'd win, yeah? Tell me this is it.\" - Tony Stark (Iron Man)",
                "\"I'm a huge fan of the way you lose control and turn into an enormous green rage monster.\" - Tony Stark (Iron Man)"

        ));
        executorService.submit(() -> tcp());
        executorService.submit(() -> udp());

    }

    
    private static void tcp(){

    }

    //Accepts a UDP request and generates a random quote as a response
    private static void udp() {
        try {
            while (true) {
                DatagramSocket socket = new DatagramSocket(17);
                int index = random.nextInt(quotes.size());
                DatagramPacket request = new DatagramPacket(new byte[1], 1);
                socket.receive(request);

                String quote = quotes.get(index);
                byte[] buffer = quote.getBytes();

                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();

                DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
                System.out.println("Waiting on server...");
                socket.send(response);
                System.out.println("Response Sent Sucessfully!");
                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}