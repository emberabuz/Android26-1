package utils

object Translit {
    fun convert(text: String): String {
        val map = mapOf(
            'А' to "A", 'а' to "a",
            'Б' to "B", 'б' to "b",
            'В' to "V", 'в' to "v",
            'Г' to "G", 'г' to "g",
            'Д' to "D", 'д' to "d",
            'Е' to "E", 'е' to "e",
            'Ё' to "Yo", 'ё' to "yo",
            'Ж' to "Zh", 'ж' to "zh",
            'З' to "Z", 'з' to "z",
            'И' to "I", 'и' to "i",
            'Й' to "Y", 'й' to "y",
            'К' to "K", 'к' to "k",
            'Л' to "L", 'л' to "l",
            'М' to "M", 'м' to "m",
            'Н' to "N", 'н' to "n",
            'О' to "O", 'о' to "o",
            'П' to "P", 'п' to "p",
            'Р' to "R", 'р' to "r",
            'С' to "S", 'с' to "s",
            'Т' to "T", 'т' to "t",
            'У' to "U", 'у' to "u",
            'Ф' to "F", 'ф' to "f",
            'Х' to "Kh", 'х' to "kh",
            'Ц' to "Ts", 'ц' to "ts",
            'Ч' to "Ch", 'ч' to "ch",
            'Ш' to "Sh", 'ш' to "sh",
            'Щ' to "Sch", 'щ' to "sch",
            'Ъ' to "", 'ъ' to "",
            'Ы' to "Y", 'ы' to "y",
            'Ь' to "", 'ь' to "",
            'Э' to "E", 'э' to "e",
            'Ю' to "Yu", 'ю' to "yu",
            'Я' to "Ya", 'я' to "ya"
        )

        val result = StringBuilder()
        for (char in text) {
            result.append(map[char] ?: char)
        }
        return result.toString()
    }
}
