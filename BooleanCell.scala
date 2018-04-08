case class BooleanCell(
	private val state: Boolean
) extends Cell(state) {
	def reproduce(state: Boolean): Cell = BooleanCell(state)

	def address(implicit conway: Conway): (Int, Int) = conway addressOf this

	def neighbors(implicit conway: Conway): Seq[Cell] = conway neighborsOf this

	def tick(implicit conway: Conway): Cell = BooleanCell(
		conway determine(this, conway neighborsOf this)
	)

	override def toString: String = {
		if (state) "*"
		else " "
	}
}
