package com.mcecelja.pocket.common.utils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public class JsonTreeHelper {

	public static void addElementToJsonTree(JsonNode mainTree, String nodeName, JsonNode branch)
	{
		if (mainTree.has(nodeName))
		{
			((ObjectNode) mainTree).remove(nodeName);
		}
		((ObjectNode) mainTree).put(nodeName, branch);
	}
}
