package daytime

import java.io.InputStreamReader
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yupenglei on 18/1/2.
 */
class Daytime {
    companion object {
        private fun parseDate(s: String): Date {
            val pieces = s.split(" ")
            val dateTime = "${pieces[1]} ${pieces[2]} UTC"
            val format = SimpleDateFormat("yy-MM-dd hh:mm:ss z")
            return format.parse(dateTime)
        }

        fun getDateFromNetwork(): Date {
            try {
                //创建时已建立网络连接
                val socket = Socket("time.nist.gov", 13)
                socket.soTimeout = 15000
                val reader = InputStreamReader(socket.getInputStream(), "ASCII")
                return reader.readText().apply { println(this) }
                        .let(this::parseDate)
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
