package parser

import model.*

object CsvParser {
    fun getPlayers(fileName: String = "fakePlayers.csv"): List<Player> {
        val inputStream = object {}.javaClass
            .getResourceAsStream("/$fileName")
            ?: throw IllegalArgumentException("Файл $fileName не найден в resources")

        return inputStream.bufferedReader(Charsets.UTF_8).useLines { lines ->
            lines
                .drop(1)
                .filter { it.isNotBlank() }
                .map { line -> line.toPlayer() }
                .toList()
        }
    }
}

private fun String.toPlayer(): Player {
    val parts = split(";")

    fun safeInt(index: Int) = parts.getOrNull(index)?.toIntOrNull() ?: 0

    return Player(
        name = parts[0],
        team = Team(parts[1], parts[2]),
        position = Position.valueOf(parts[3]),
        nationality = parts[4],
        agency = parts[5],
        transferCost = safeInt(6),
        participations = safeInt(7),
        goals = safeInt(8),
        assists = safeInt(9),
        yellowCards = safeInt(10),
        redCards = safeInt(11)
    )
}