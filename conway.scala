//import scala.collection.immutable
import scala.util.Try
import scala.util.Success
import scala.util.Failure

class Node(
	private var state: Boolean, 
	private val x: Int, 
	private val y: Int
) {
	private var futureState: Boolean = false

	def isAlive: Boolean = {
		return state
	}

	def address: (Int, Int) = {
		(x, y)
	}

	def determine(neighbors: Int): Unit = {
		if (state) {
			if (neighbors < 2) this futureState = false
			else if (neighbors >= 2 && neighbors <= 3) this futureState = true
			else this futureState = false
		} else {
			if (neighbors == 3) this futureState = true
		}
	}

	def tick: Unit = {
		this state = futureState
	}

	override def toString: String = {
		if (state) "*"
		else " "
	}
}

class World(val width: Int, val height: Int) {
	val nodes: Array[Array[Node]] = Array.tabulate[Node](width, height) {
		(i, j) => new Node(true, i, j)
	}
	override def toString: String = {
		//nodes.map(x => x.toString).fold("")((x, a) => x + a)
		nodes.map(_.mkString + "\n").mkString
	}

	def neighbors(node: Node): Int = {
		val deltas: List[(Int, Int)] = List[(Int, Int)](
			(-1, 0), (1, 0), (0, -1), (0, 1)
		)
		
		val (x, y): (Int, Int) = node.address

		deltas map {
			case (i, j) => (x + i, y + j)
		} map {
			case (a, b) => Try {
				nodes(a)(b).isAlive
			} match {
				case Success(x) => x
				case Failure(e) => false
			}
		} count(_ == true)
	}

	def tick: Unit = {
		for { i <- 0 until width; j <- 0 until height } 
		yield {
			val node: Node = nodes(i)(j)
			val neighbors: Int = this.neighbors(node)
			node.determine(neighbors)
		}

		for { i <- 0 until width; j <- 0 until height } 
		yield {
			val node: Node = nodes(i)(j)
			node.tick
		}
	}
}

println("running ...\n")
val world: World = new World(10, 10)
println(world.toString())
world.tick
println(world.toString())
world.tick
println(world.toString())
world.tick
println(world.toString())
world.tick
println(world.toString())
world.tick
println(world.toString())
