import parser.CsvParser
import resolver.Resolver
import utils.Translit
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.data.general.DefaultPieDataset
import java.io.File

fun main() {
    println(Translit.convert("==================================="))
    println(Translit.convert("PRAKTICHESKAYA RABOTA - VARIANT 4"))
    println(Translit.convert("==================================="))

    // Zagruzhaem dannye
    val allPlayers = CsvParser.getPlayers()
    val analyzer = Resolver(allPlayers)

    // Zadanie 1 - Igroki bez agentstva
    println(Translit.convert("\n[1] Igroki bez agentstva:"))
    val withoutAgency = analyzer.getCountWithoutAgency()
    println("    -> $withoutAgency chelovek")

    // Zadanie 2 - Luchshiy zashchitnik bombardir
    println(Translit.convert("\n[2] Luchshiy zashchitnik-bombardir:"))
    val (bestScorer, goalsAmount) = analyzer.getBestScorerDefender()
    println("    -> $bestScorer (goly: $goalsAmount)")

    // Zadanie 3 - Poziciya samogo dorogogo nemca
    println(Translit.convert("\n[3] Poziciya samogo dorogogo nemeckogo igroka:"))
    val germanPosition = analyzer.getTheExpensiveGermanPlayerPosition()
    println("    -> ${Translit.convert(germanPosition)}")

    // Zadanie 4 - Samaya grubaya komanda
    println(Translit.convert("\n[4] Komanda s naibolshim srednim chislom krasnyh kartochek:"))
    val rudeTeam = analyzer.getTheRudestTeam()
    println("    -> ${rudeTeam.name} (${rudeTeam.city})")

    // Vizualizaciya - diagramma po stranam
    println(Translit.convert("\n[5] Sozdanie diagrammy raspredeleniya po stranam..."))
    val countryStats = analyzer.getPlayersByCountry()

    // Vyvod statistiki v konsol
    println(Translit.convert("\nSTATISTIKA PO STRANAM:"))
    println(Translit.convert("---------------------"))

    val sortedCountries = countryStats.toList().sortedByDescending { (_, count) -> count }

    for ((index, pair) in sortedCountries.withIndex()) {
        val (country, count) = pair
        println("${index + 1}. $country: $count")
    }

    // Sohranyaem diagrammu
    savePieChart(countryStats, "countries_pie_chart.png")
    println(Translit.convert("\n✅ Diagramma sohranena v fail: countries_pie_chart.png"))

    // Dopolnitelnaya informaciya
    println(Translit.convert("\n" + "=".repeat(40)))
    println(Translit.convert("VSEGO PROANALIZIROVANO: ${allPlayers.size} igrokov"))
    println(Translit.convert("=".repeat(40)))
}

fun savePieChart(data: Map<String, Int>, fileName: String) {
    val dataset = DefaultPieDataset<String>()

    // Zapolnyaem dannyami
    for ((category, value) in data) {
        dataset.setValue(category, value.toDouble())
    }

    // Sozdaem diagrammu
    val chart = ChartFactory.createPieChart(
        Translit.convert("Raspredelenie igrokov po stranam"),
        dataset,
        true,  // legenda
        true,  // podskazki
        false  // URL
    )

    // Sohranyaem kak PNG
    val width = 800
    val height = 600
    ChartUtils.saveChartAsPNG(File(fileName), chart, width, height)
}