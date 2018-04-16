case class SmartCell(
	private val state: Boolean,
	private val addr: (Int, Int) = (0, 0),
	private val neighbs: Seq[SmartCell] = Seq(
		SmartCell, SmartCell, SmartCell,
		SmartCell, SmartCell,
		SmartCell, SmartCell, SmartCell
	)
) extends Cell[SmartCell](state) with Addressed with Interlinked[SmartCell] {
	def reproduce(index: Index)(implicit conway: Conway[SmartCell]): SmartCell = SmartCell(
		this state,
		conway addressOf index,
		this neighbors // tmp, needs to be rewritten
	)

	def address: (Int, Int) = this addr

	def neighbors: Seq[SmartCell] = this neighbs

	def tick(implicit conway: Conway[SmartCell]): SmartCell = SmartCell(
		conway determine(this, this neighbors), this addr, this neighbs
	)
	
	override def toString: String = {
		if (state) "*"
		else " "
	}
}

object SmartCell 
extends SmartCell(false, (0, 0), Seq()) 
with Interlinked[SmartCell] 
