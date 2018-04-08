#!/bin/sh
rm *.class
mv conway.scala conway.sc
scalac *.scala
mv conway.sc conway.scala
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

		var conway: Conway = new FiniteConway[AddressedCell](
			5, 5,
			AddressedCell(false, (0, 0)),
			blinker
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
