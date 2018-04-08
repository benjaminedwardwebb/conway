trait Addressable extends Cell {
	def address: (Int, Int)
}

case class AddressableCell(
	private val state: Boolean,
	private val addr: (Int, Int)
) extends Cell(state) with Addressable {
	def reproduce(state: Boolean, address: (Int, Int)): Cell = AddressableCell(
		state, address
	)

	def address: (Int, Int) = this addr

	def neighbors(implicit conway: Conway): Seq[Cell] = conway neighborsOf this

	def tick(implicit conway: Conway): Cell = AddressableCell(
		conway determine(this, conway neighborsOf this),
		this addr
	)

	override def toString: String = {
		if (state) "*"
		else " "
	}
}
