case class AddressedCell(
	private val state: Boolean,
	private val addr: Address
) extends Cell[AddressedCell](state) with Addressed {
	override def reproduce(index: Index)(implicit conway: Conway[AddressedCell]): AddressedCell = {
		AddressedCell(this state, conway addressOf index)
	}

	def address: Address = this addr

	override def tick(implicit conway: Conway[AddressedCell]): AddressedCell = AddressedCell(
		conway determine(this, conway neighborsOf this),
		this addr
	)

	override def toString: String = {
		if (state) "*"
		else " "
	}
}
