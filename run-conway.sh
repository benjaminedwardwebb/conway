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

		var cells: Vector[AddressableCell] = Vector(
			AddressableCell(true, (0, 0)),
			AddressableCell(false, (1, 0)), 
			AddressableCell(false, (2, 0)),
			AddressableCell(false, (0, 1)), 
			AddressableCell(true, (1, 1)), 
			AddressableCell(false, (2, 1)),
			AddressableCell(true, (0, 2)), 
			AddressableCell(false, (1, 2)), 
			AddressableCell(true, (2, 2))
		)

		var conway: Conway = new SmartConway[AddressableCell](3, 3, cells)

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
