abstract class Cell(private val state: Boolean) {
	def isAlive: Boolean = this state
	def tick(implicit conway: Conway): Cell
	def toString: String
}
