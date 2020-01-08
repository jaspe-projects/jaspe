package eu.jaspe.jaspe.parser;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 *
 */
public class JaspeParser {

  private static final char FIELD_DELIMITER = ',';
  private static final String FIELD_SUBQUERY_DELIMITER = ".";
  private static final String FIELD_SUBQUERY_OPEN = "(";
  private static final String FIELD_SUBQUERY_CLOSE = ")";

  /**
   *
   * @param node
   * @param fields
   * @param parentField
   */
  public static void parse(JsonNode node, Set<String> fields, String parentField) {

    Iterator<Map.Entry<String, JsonNode>> fieldsIterator = node.fields();

    while (fieldsIterator.hasNext()) {
      Map.Entry<String, JsonNode> mapEntry = fieldsIterator.next();
      String entryKey = mapEntry.getKey();
      JsonNode entryValue = mapEntry.getValue();
      String fieldPath;

      if (!parentField.isEmpty()) {
        fieldPath = parentField + "." + entryKey;
      } else {
        fieldPath = entryKey;
      }

      boolean contains = false;
      for (String field : fields) {
        if (field.equals(fieldPath) || field.startsWith(fieldPath + FIELD_SUBQUERY_DELIMITER) || field.equals("*")) {
          contains = true;
          break;
        }
      }

      if (!contains) {
        fieldsIterator.remove();
      } else {
        if (entryValue.isObject() || entryValue.isArray()) {

          Set<String> subFields = new HashSet<>();
          for (Iterator<String> iterator = fields.iterator(); iterator.hasNext(); ) {
            String requiredField = iterator.next();
            if (requiredField.contains(entryKey + FIELD_SUBQUERY_DELIMITER)) {
              subFields.add(requiredField);
            }
          }

          if (subFields.isEmpty()) {
            subFields.add("*");
          }

          if (entryValue.isObject()) {
            parse(entryValue, subFields, fieldPath);
          } else if (entryValue.isArray()) {
            if (!fields.isEmpty()) {
              for (JsonNode arrayElementNode : entryValue) {
                parse(arrayElementNode, subFields, fieldPath);
              }
            }
          }

        }
      }
    }
  }

  /**
   *
   * @param buffer
   * @return
   */
  public static String buildQuery(StringBuilder buffer) {
    StringBuilder query = new StringBuilder();
    StringBuilder field = new StringBuilder();
    String parentField = "";
    int index = 0;

    while (true) {
      if (buffer.charAt(index) == FIELD_DELIMITER) {
        index++;
        query.append(FIELD_DELIMITER);
        if (!parentField.isEmpty()) {
          query.append(parentField + FIELD_SUBQUERY_DELIMITER);
        }
        field = new StringBuilder();
      } else if (buffer.substring(index).startsWith(FIELD_SUBQUERY_OPEN)) {
        index++;
        query.append(FIELD_SUBQUERY_DELIMITER);
        if (parentField.isEmpty()) {
          parentField = field.toString();
        } else {
          parentField = parentField.concat(FIELD_SUBQUERY_DELIMITER).concat((field.toString()));
        }
        field = new StringBuilder();
      } else if (buffer.substring(index).startsWith(FIELD_SUBQUERY_CLOSE)) {
        index++;
        if (parentField.indexOf(FIELD_SUBQUERY_DELIMITER) != -1) {
          parentField = parentField.substring(0, parentField.indexOf(FIELD_SUBQUERY_DELIMITER));
        } else {
          parentField = "";
        }
      } else {
        field.append(buffer.charAt(index));
        query.append(buffer.charAt(index));
        index++;
      }

      if (index >= buffer.length()) {
        break;
      }
    }
    return query.toString();
  }
}
