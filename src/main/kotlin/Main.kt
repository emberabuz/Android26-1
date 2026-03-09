import parser.CsvParser
import resolver.Resolver
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.data.general.DefaultPieDataset
import java.io.File

fun main() {
    println("практика - вариант 4")

    val players = CsvParser.getPlayers()
    val resolver = Resolver(players)

    // Задание 1
    println("\n[1] Игроки без агентства:")
    println("    -> ${resolver.getCountWithoutAgency()} человек")

    // Задание 2
    println("\n[2] Лучший защитник-бомбардир:")
    val (bestScorer, goals) = resolver.getBestScorerDefender()
    println("    -> $bestScorer забил $goals голов")

    // Задание 3
    println("\n[3] Позиция самого дорогого немецкого игрока:")
    println("    -> ${resolver.getTheExpensiveGermanPlayerPosition()}")

    // Задание 4
    println("\n[4] Самая грубая команда:")
    val rudeTeam = resolver.getTheRudestTeam()
    println("    -> ${rudeTeam.name} (${rudeTeam.city})")

    // Визуализация
    println("\n[5] Создание диаграммы распределения по странам...")
    val countryStats = resolver.getPlayersByCountry()

    println("\nСТАТИСТИКА ПО СТРАНАМ:")
    println("---------------------")
    countryStats.toList()
        .sortedByDescending { it.second }
        .forEachIndexed { index, (country, count) ->
            println("${index + 1}. $country: $count")
        }

    saveCountryChart(countryStats, "countries_pie_chart.png")
    println("\n Диаграмма сохранена: countries_pie_chart.png")
}

private fun saveCountryChart(data: Map<String, Int>, fileName: String) {
    val dataset = DefaultPieDataset<String>()
    data.forEach { (country, count) ->
        dataset.setValue(country, count.toDouble())
    }

    val chart = ChartFactory.createPieChart(
        "Распределение игроков по странам",
        dataset,
        true,
        true,
        false
    )

    ChartUtils.saveChartAsPNG(File(fileName), chart, 800, 600)
}