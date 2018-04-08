trait Addressable extends Cell {
	def address: (Int, Int)
}

case class AddressedCell(
	private val state: Boolean,
	private val addr: (Int, Int)
) extends Cell(state) with Addressable {
	def reproduce(state: Boolean, address: (Int, Int)): Cell = AddressedCell(
		state, address
	)

	def address: (Int, Int) = this addr

	def tick(implicit conway: Conway): Cell = AddressedCell(
		conway determine(this, conway neighborsOf this),
		this addr
	)

	override def toString: String = {
		if (state) "*"
		else " "
	}
}
