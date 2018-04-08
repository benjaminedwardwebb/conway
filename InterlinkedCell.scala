trait Interlinkable extends Cell {
	def neighbors: Seq[Cell]
}

object EndCell extends Cell(false) with Interlinkable {
	def reproduce: Cell = this
	def neighbors: Seq[Cell] = Seq(this)
	def tick(implicit conway: Conway): Cell = this
	override def toString: String = " "
}

case class InterlinkedCell (
	private val state: Boolean = false,
	private val topLeft: Cell = EndCell,
	private val top: Cell = EndCell,
	private val topRight: Cell = EndCell,
	private val left: Cell = EndCell,
	private val right: Cell = EndCell,
	private val bottomLeft: Cell = EndCell ,
	private val bottom: Cell = EndCell,
	private val bottomRight: Cell = EndCell
) extends Cell(state) with Interlinkable {
	def reproduce(
		state: Boolean,
		topLeft: Cell, top: Cell, topRight: Cell,
		left: Cell, right: Cell,
		bottomLeft: Cell, bottom: Cell, bottomRight: Cell
	): Cell = InterlinkedCell(
		state,
		topLeft, top, topRight,
		left, right,
		bottomLeft, bottom, bottomRight
	)

	def neighbors: Seq[Cell] = Seq(
		topLeft, top, topRight,
		left, right,
		bottomLeft, bottom, bottomRight
	)

	def tick(implicit conway: Conway): Cell = InterlinkedCell(
		conway determine(this, this neighbors),
		topLeft, top, topRight,
		left, right,
		bottomLeft, bottom, bottomRight
	)
	
	override def toString: String = {
		if (state) "*"
		else " "
	}
}
