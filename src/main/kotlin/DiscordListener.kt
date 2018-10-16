import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import org.jsoup.Jsoup

class DiscordListener(val bot: Bot) : ListenerAdapter() {
    private val duck = "https://duckduckgo.com/?q="
    private val google = "https://www.google.com/search?q="
    private val youtube = "https://www.youtube.com/results?search_query="
    private val bing = "https://www.bing.com/search?q="

    override fun onMessageReceived(event: MessageReceivedEvent) {
        //log chat into the console
        println("${event.author.name}: ${event.message.contentRaw}")


        //if the bot is sending a message ignore it
        if(event.author.isBot())
            return

        //list of commands for bot
        if(event.message.contentRaw == "!help")
            event.channel.sendMessage(
                    "List of Commands: \n" +
                            "Introduction: Hi \n" +
                            "All Search : !a {query} \n"+
                            "Bing Search : !b {query} \n"+
                            "DuckDuckGo Search : !d {query} \n" +
                            "Google Search : !g {query} \n" +
                            "Google Search Top 5 : !gr {query} \n"+
                            "Youtube Search : !y {query} \n" +
                            "Youtube Search Top 5 : !yr {query}"
            ).queue()

        val commandArray = arrayOf("!y", "!Y", "!g", "!G", "!b", "!B" , "!d" , "!D")
        for(c in commandArray)
            if (event.message.contentRaw == c)
                event.channel.sendMessage("Please type in " + c + " followed by an argument").queue()

        if(event.message.contentRaw == "Hi" || event.message.contentRaw == "hi" || event.message.contentRaw == "hello" || event.message.contentRaw == "Hello")
        event.channel.sendMessage(
                "Hi. My name is HackBot \n" +
                        "I'm here to assist you in your exotic adventure to find the perfect tutorial for your Hackathon app").queue()

        if(event.message.contentRaw.take(3) == "!d " || event.message.contentRaw.take(3) == "!D ") {
            try {
                var query = event.message.contentRaw.takeLast(event.message.contentRaw.length - 3).trimStart()
                val noRepeatedSpaces = query.replace("\\s+".toRegex(), " ")
                val spacesRemoved = noRepeatedSpaces.replace(" ", "+")
                event.channel.sendMessage("Your search result: " + event.message.contentRaw.takeLast(event.message.contentRaw.length - 3)).queue()
                event.channel.sendMessage(duck + spacesRemoved).queue()
            }
            catch(e: IllegalArgumentException) {
                event.channel.sendMessage("Please Type a Query").queue()
            }
        }

        if(event.message.contentRaw.take(3) == "!g " || event.message.contentRaw.take(3) == "!G ") {
            try {
                var query = event.message.contentRaw.takeLast(event.message.contentRaw.length - 3).trimStart()
                val noRepeatedSpaces = query.replace("\\s+".toRegex(), " ")
                val spacesRemoved = noRepeatedSpaces.replace(" ", "+")
                event.channel.sendMessage("Your search result: " + event.message.contentRaw.takeLast(event.message.contentRaw.length - 3)).queue()
                event.channel.sendMessage(google + spacesRemoved).queue()

            }
            catch(e: IllegalArgumentException) {
                event.channel.sendMessage("Please Type a Query").queue()
            }
        }

        if(event.message.contentRaw.take(4) == "!gr " || event.message.contentRaw.take(4) == "!GR " || event.message.contentRaw.take(4) == "!Gr " || event.message.contentRaw.take(4) == "!gR ") {
            try {
                var query = event.message.contentRaw.takeLast(event.message.contentRaw.length - 4).trimStart()
                val noRepeatedSpaces = query.replace("\\s+".toRegex(), " ")
                val spacesRemoved = noRepeatedSpaces.replace(" ", "+")
                event.channel.sendMessage("Your search result: " + event.message.contentRaw.takeLast(event.message.contentRaw.length - 4)).queue()


                Jsoup.connect(google + spacesRemoved).get().run {
                    //2. Parses and scrapes the HTML response
                    if (select("div.rc").isNotEmpty()) {
                        var counter = 1
                        select("div.rc").forEachIndexed { index, element ->
                            if (counter <= 5) {
                                val titleAnchor = element.select("h3 a")
                                println(titleAnchor)
                                val title = titleAnchor.text()
                                val url = titleAnchor.attr("href")
                                //3. Dumping Search Index, Title and URL on the stdout.
                                event.channel.sendMessage("$counter. $title ($url)").queue()
                                counter++
                            }
                            else
                                return
                        }
                    }
                    else
                        event.channel.sendMessage("Sorry, your search query returned no values. Please try again.").queue()
                }
            }
            catch(e: IllegalArgumentException) {
                event.channel.sendMessage("Please Type a Query").queue()
            }
        }

        if(event.message.contentRaw.take(3) == "!b " || event.message.contentRaw.take(3) == "!B ") {
            try {
                var query = event.message.contentRaw.takeLast(event.message.contentRaw.length - 3).trimStart()
                val noRepeatedSpaces = query.replace("\\s+".toRegex(), " ")
                val spacesRemoved = noRepeatedSpaces.replace(" ", "+")
                event.channel.sendMessage("Your search result: " + event.message.contentRaw.takeLast(event.message.contentRaw.length - 3)).queue()
                event.channel.sendMessage(bing + spacesRemoved).queue()
            }
            catch(e: IllegalArgumentException) {
                event.channel.sendMessage("Please Type a Query").queue()
            }
        }

        if(event.message.contentRaw.take(3) == "!y " || event.message.contentRaw.take(3) == "!Y ") {
            try {
                var query = event.message.contentRaw.takeLast(event.message.contentRaw.length - 3).trimStart()
                val noRepeatedSpaces = query.replace("\\s+".toRegex(), " ")
                val spacesRemoved = noRepeatedSpaces.replace(" ", "+")
                event.channel.sendMessage("Your search result: " + event.message.contentRaw.takeLast(event.message.contentRaw.length - 3)).queue()
                event.channel.sendMessage(youtube + spacesRemoved).queue()
            }
            catch(e: IllegalArgumentException) {
                event.channel.sendMessage("Please Type a Query").queue()
            }
        }

        if(event.message.contentRaw.take(3) == "!a " || event.message.contentRaw.take(3) == "!A ") {
            try {
                var query = event.message.contentRaw.takeLast(event.message.contentRaw.length - 3).trimStart()
                val noRepeatedSpaces = query.replace("\\s+".toRegex(), " ")
                val spacesRemoved = noRepeatedSpaces.replace(" ", "+")
                event.channel.sendMessage("Your search result: " + event.message.contentRaw.takeLast(event.message.contentRaw.length - 3)).queue()
                event.channel.sendMessage(bing + spacesRemoved).queue()
                event.channel.sendMessage(duck + spacesRemoved).queue()
                event.channel.sendMessage(google + spacesRemoved).queue()
                event.channel.sendMessage(youtube + spacesRemoved).queue()
            }
            catch(e: IllegalArgumentException) {
                event.channel.sendMessage("Please Type a Query").queue()
            }
        }

        if(event.message.contentRaw.take(4) == "!yr " || event.message.contentRaw.take(4) == "!YR " || event.message.contentRaw.take(4) == "!Yr " || event.message.contentRaw.take(4) == "!yR ") {
            try {
                var query = event.message.contentRaw.takeLast(event.message.contentRaw.length - 4).trimStart()
                val noRepeatedSpaces = query.replace("\\s+".toRegex(), " ")
                val spacesRemoved = noRepeatedSpaces.replace(" ", "+")
                event.channel.sendMessage("Your search result: " + event.message.contentRaw.takeLast(event.message.contentRaw.length - 4)).queue()

                val doc = Jsoup.connect(youtube + spacesRemoved)
                        .data("search_query", query)
                        .userAgent("Mozilla/5.0")
                        .get()
                var counter = 1
                if(doc.select(".yt-lockup-title > a[title]").size >= 2){
                    for (a in doc.select(".yt-lockup-title > a[title]")) {
                        if(counter <= 5) {
                            if(a.attr("href").length <= 60) {
                                event.channel.sendMessage("$counter https://www.youtube.com" + a.attr("href")).queue()
                                counter++
                            }
                        }
                    }
                }
                else
                    event.channel.sendMessage("Sorry, your search query returned no values. Please try again.").queue()
            }
            catch(e: IllegalArgumentException) {
                event.channel.sendMessage("Please Type a Query").queue()
            }
        }
    }
}