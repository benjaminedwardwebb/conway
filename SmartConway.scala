import scala.util.{ Try, Success, Failure }

class SmartConway[+C <: Cell](
	val w: Int, 
	val h: Int,
	val cells: Vector[Cell]
) extends Conway {
	implicit val conway: Conway = this
	private val deltas = Seq(
		(-1, -1), (0, -1), (1, -1),
		(-1, 0), (1, 0),
		(-1, 1), (0, 1), (1, 1)
	)

	def indexOf(cell: Cell): Int = cells indexOf cell
	def indexOf(address: (Int, Int)): Int = address._2 * w + address._1

	def addressOf(cell: Cell): (Int, Int) = cell match {
		case cell: Addressable => cell address
		case cell: Cell => this addressOf(this indexOf cell)
	}
	def addressOf(index: Int): (Int, Int) = (index % w, index / w)

	def getCellAt(address: (Int, Int)): Option[Cell] = Try {
		cells(this indexOf address)
	} match {
		case Success(cell) => Some(cell)
		case Failure(e) => None
	}

	def neighborsOf(cell: Cell): Seq[Cell] = cell match {
		case cell: Interlinked => cell.neighbors
		case cell: AddressableCell => {
			deltas map { 
				case (i, j) => (cell.address._1 + i, cell.address._2 + j)
			} flatMap {
				address => this getCellAt address
			}
		}
		case cell: Cell => {
			val address = this addressOf(cells indexOf cell)
			deltas map { 
				case (i, j) => (address._1 + i, address._2 + j)
			} flatMap {
				address => this getCellAt address
			}
		}
	}

	def determine(cell: Cell, neighbors: Seq[Cell]): Boolean = {
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

	def tick: SmartConway[C] = new SmartConway(
		w, h, cells map { cell => cell.tick(this) }
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
