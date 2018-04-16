case class BooleanCell(
	private val state: Boolean
) extends Cell[BooleanCell](state) {
	def reproduce(index: Index)(implicit conway: Conway[BooleanCell]): BooleanCell = BooleanCell(
		this state
	)

	def tick(implicit conway: Conway[BooleanCell]): BooleanCell = BooleanCell(
		conway determine(this, conway neighborsOf this)
	)

	override def toString: String = {
		if (state) "*"
		else " "
	}
}
