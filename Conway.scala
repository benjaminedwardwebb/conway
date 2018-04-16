abstract class Conway[C <: Cell[C]] {
	val cells: Seq[C]
	def addressOf(addressable: Addressable): Address
	def indexOf(indexable: Indexable): Index
	def neighborsOf(cell: C): Seq[C]
	def determine(cell: C, neighbors: Seq[C]): Boolean
	def tick: Conway[C]
	def toString: String
}
object Conway {
	val deltas: Seq[(Int, Int)] = Seq(
		(-1, -1), (0, -1), (1, -1),
		(-1, 0), (1, 0),
		(-1, 1), (0, 1), (1, 1)
	)
}

abstract class Cell[C <: Cell[C]](
	private val state: Boolean
) extends Addressable with Indexable { self: C =>
	def reproduce(index: Index)(implicit conway: Conway[C]): C
	def isAlive: Boolean = this state
	def tick(implicit conway: Conway[C]): C
	def toString: String
}

trait Addressable
object Addressable {
	implicit def int2Addressable(int: Int): Addressable = Index(int)
}

trait Addressed extends Addressable {
	def address: (Int, Int)
}

case class Index(val int: Int) extends Addressable
object Index extends Addressable {
	implicit def index2Int(index: Index): Int = index int
	implicit def int2Index(int: Int): Index = Index(int)
}

trait Indexable
object Indexable {
	implicit def tuple2Indexable(tuple: (Int, Int)): Indexable = Address(tuple)
}

case class Address(val tuple: (Int, Int)) extends Indexable
object Address extends Indexable {
	implicit def address2Tuple(address: Address): (Int, Int) = address tuple
	implicit def tuple2Address(tuple: (Int, Int)): Address = Address(tuple)
}

trait Interlinked[C <: Cell[C]] {
	def neighbors: Seq[C]
}
