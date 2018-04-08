abstract class Conway() {
	def addressOf(index: Int): (Int, Int)
	def addressOf(cell: Cell): (Int, Int)
	def indexOf(address: (Int, Int)): Int
	def neighborsOf(cell: Cell): Seq[Cell]
	def determine(cell: Cell, neighbors: Seq[Cell]): Boolean
	def tick: Conway
	def toString: String
}
