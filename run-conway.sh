#!/bin/sh
rm *.class > /dev/null 2>&1
scalac *.scala
if [ "$?" != "0" ]; then
	exit 1
fi
exec scala "$0" "$@"
!#
object RunConway {
	def main(args: Array[String]) {
		println("start\n-----")

		var blinker: Vector[AddressedCell] = Vector(
			AddressedCell(true, (1, 1)),
			AddressedCell(true, (0, 1)),
			AddressedCell(true, (2, 1))
		)

		var conway: Conway[AddressedCell] = new FiniteConway[AddressedCell](
			AddressedCell(false, (0, 0)),
			blinker,
			5, 5
		)

		print(conway.toString)

		for (i <- 1 to 10) {
			println(s"--- $i")
			conway = conway.tick
			print(conway.toString)
		}

		println("---\nend")
	}
}

RunConway.main(args)
