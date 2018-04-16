case class InterlinkedCell(
	private val state: Boolean = false,
	private val neighbs: Seq[InterlinkedCell] = Seq(
		InterlinkedCell, InterlinkedCell, InterlinkedCell,
		InterlinkedCell, InterlinkedCell,
		InterlinkedCell, InterlinkedCell, InterlinkedCell
	)
) extends Cell[InterlinkedCell](state) with Interlinked[InterlinkedCell] {
	def reproduce(index: Index)(implicit conway: Conway[InterlinkedCell]): InterlinkedCell = {
		val address = conway addressOf index
		val neighbors = Conway.deltas.map {
			case (i, j) => (address._1 + i, address._2 + j)
		} map {
			address => conway indexOf address
		} map {
			index => conway cells index
		}

		InterlinkedCell(this state, neighbors)
	}

	def neighbors: Seq[InterlinkedCell] = this neighbs

	def tick(implicit conway: Conway[InterlinkedCell]): InterlinkedCell = InterlinkedCell(
		conway determine(this, this neighbors), this neighbors
	)
	
	override def toString: String = {
		if (state) "*"
		else " "
	}
}

object InterlinkedCell 
extends InterlinkedCell(false, Seq()) 
with Interlinked[InterlinkedCell]
