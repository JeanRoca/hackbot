object BotLauncher{
    @JvmStatic
    fun main(args: Array<String>){
        val config = Config()
        val bot = Bot(config.token)
        bot.start()
    }
}