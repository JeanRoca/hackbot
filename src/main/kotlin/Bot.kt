import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder

class Bot(val token: String) {

    fun start() {
        val discordListener = DiscordListener(this)
        val builder = JDABuilder(AccountType.BOT)
        builder.setToken(token)
        builder.addEventListener(discordListener)
        builder.build()


    }
}