case class SmartCell(
	private val state: Boolean,
	private val addr: (Int, Int) = (0, 0),
	private val topLeft: Cell = EndCell,
	private val top: Cell = EndCell,
	private val topRight: Cell = EndCell,
	private val left: Cell = EndCell,
	private val right: Cell = EndCell,
	private val bottomLeft: Cell = EndCell,
	private val bottom: Cell = EndCell,
	private val bottomRight: Cell = EndCell
) extends Cell(state) with Addressable with Interlinkable {
	def reproduce(
		state: Boolean, address: (Int, Int), neighbors: Seq[Cell]
	): Cell = SmartCell(
		state, address,
		neighbors(0), neighbors(1), neighbors(2),
		neighbors(3), neighbors(4),
		neighbors(5), neighbors(6), neighbors(7)
	)

	def address: (Int, Int) = this addr

	def neighbors: Seq[Cell] = Seq(
		topLeft, top, topRight,
		left, right,
		bottomLeft, bottom, bottomRight
	)

	def tick(implicit conway: Conway): Cell = SmartCell(
		conway determine(this, this neighbors), this addr,
		topLeft, top, topRight,
		left, right,
		bottomLeft, bottom, bottomRight
	)
	
	override def toString: String = {
		if (state) "*"
		else " "
	}
}
