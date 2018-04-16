import scala.util.{ Try, Success, Failure }

class FiniteConway[C <: Cell[C]](
	val default: C,
	val initial: Vector[C],
	val w: Int, 
	val h: Int
) extends Conway[C] {
	implicit val conway: Conway[C] = this

	val cells: Vector[C] = Vector.tabulate(w * h) { i =>
		val address = this addressOf i

		initial find { cell => this.addressOf(cell) == address } match {
			case Some(cell) => cell
			case None => default reproduce i
		}
	}

	def addressOf(addressable: Addressable): Address = addressable match {
		case cell: Addressed => cell address
		// case cell: Interlinked => chech cached cell-address pairs?
		case cell: Cell[C] => this addressOf(this indexOf cell)
		case index: Index => (index % w, index / w)
	}

	def indexOf(indexable: Indexable): Index = indexable match {
		case cell: Cell[C] => cells indexOf cell
		case address: Address => address._2 * w + address._1
	}

	def getCellAt(address: (Int, Int)): Option[C] = Try {
		cells(this indexOf address)
	} match {
		case Success(cell) => Some(cell)
		case Failure(e) => None
	}

	def neighborsOf(cell: C): Seq[C] = cell match {
		case cell: Interlinked[C] => cell neighbors
		case cell: Cell[C] => {
			val address = this addressOf cell
			Conway.deltas.map { 
				case (i, j) => (address._1 + i, address._2 + j)
			} flatMap {
				address => this getCellAt address
			}
		}
	}

	def determine(cell: C, neighbors: Seq[C]): Boolean = {
		val liveNeighbors = neighbors count { cell => cell isAlive }
		if (cell isAlive) {
			if (liveNeighbors < 2) false
			else if (liveNeighbors >= 2 && liveNeighbors <= 3) true
			else false
		} else {
			if (liveNeighbors == 3) true
			else false
		}
	}

	def tick: FiniteConway[C] = new FiniteConway[C](
		default, cells map { cell => cell.tick }, w, h
	)

	override def toString: String = {
		cells map { 
			cell => {
				if ((this addressOf cell)._1 == w-1) cell.toString + "\n"
				else cell toString
			}
		} mkString
	}
}
