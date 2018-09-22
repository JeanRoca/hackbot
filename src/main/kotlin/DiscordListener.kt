import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

class DiscordListener(val bot: Bot) : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        println("${event.author.name}: ${event.message.contentRaw}")
        if(event.author.isBot())
            return
        if (event.message.contentRaw.take(8) == "!youtube") {

            try {
                val query = event.message.contentRaw.takeLast(event.message.contentRaw.length - 9)
                val spacesRemoved = query.replace(" ", "+")
                event.channel.sendMessage("you typed " + event.message.contentRaw.takeLast(event.message.contentRaw.length - 8)).queue()
                event.channel.sendMessage("https://www.youtube.com/results?search_query=" + spacesRemoved).queue()
            } catch(e: IllegalArgumentException){
                event.channel.sendMessage("please type a query").queue()
            }


        }
    }
}