#!/bin/sh
exec scala "$0" "$@"
!#
object Test {
	def main(args: Array[String]) {
		println("Hello, world")
	}
}

Test.main(args)
