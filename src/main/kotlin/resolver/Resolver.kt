package resolver

import model.Player
import model.Team
import model.Position

class Resolver(private val players: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int {
        // Другая реализация через fold
        return players.fold(0) { acc, player ->
            if (player.agency.isBlank()) acc + 1 else acc
        }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        // Другая реализация через переменные
        var bestName = ""
        var bestGoals = -1

        for (player in players) {
            if (player.position == Position.DEFENDER && player.goals > bestGoals) {
                bestGoals = player.goals
                bestName = player.name
            }
        }

        return if (bestGoals > -1) bestName to bestGoals else "" to 0
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        // Сортируем по-другому
        val germanPlayers = players.filter { it.nationality.equals("Germany", ignoreCase = true) }
        if (germanPlayers.isEmpty()) return ""

        // Сортируем вручную
        val sorted = germanPlayers.sortedByDescending { it.transferCost }
        val mostExpensive = sorted.firstOrNull() ?: return ""

        return when (mostExpensive.position) {
            Position.GOALKEEPER -> "Вратарь"
            Position.DEFENDER -> "Защитник"
            Position.MIDFIELD -> "Полузащитник"
            Position.FORWARD -> "Нападающий"
        }
    }

    override fun getTheRudestTeam(): Team {
        // Другая реализация через mutableMap
        val teamStats = mutableMapOf<Team, Pair<Int, Int>>() // сумма красных, кол-во игроков

        for (player in players) {
            val current = teamStats[player.team] ?: (0 to 0)
            teamStats[player.team] = (current.first + player.redCards) to (current.second + 1)
        }

        var worstTeam: Team? = null
        var maxAverage = -1.0

        for ((team, stats) in teamStats) {
            val average = stats.first.toDouble() / stats.second
            if (average > maxAverage) {
                maxAverage = average
                worstTeam = team
            }
        }

        return worstTeam ?: Team("", "")
    }

    fun getPlayersByCountry(): Map<String, Int> {
        // Другая реализация через mutableMap
        val result = mutableMapOf<String, Int>()
        for (player in players) {
            val country = player.nationality
            result[country] = (result[country] ?: 0) + 1
        }
        return result
    }
}