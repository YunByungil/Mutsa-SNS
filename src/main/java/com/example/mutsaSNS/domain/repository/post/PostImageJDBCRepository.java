package com.example.mutsaSNS.domain.repository.post;

import com.example.mutsaSNS.domain.entity.post.PostImage;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostImageJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    public PostImageJDBCRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void customBulkInsert(List<PostImage> postImages) {
        String sql = "insert into post_image " +
                "(post_id, image) values (?, ?)"; // bulk insert에 사용할 기본 쿼리

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() { //
            @Override // insert할 모델의 값을 사용합니다.
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                PostImage postImage = postImages.get(i);
                ps.setLong(1, postImage.getPost().getId()); // 쿼리의 ?의 순서대로 1번으로 할당되며 ? 대신 치환
                ps.setString(2, postImage.getImage());
            }

            @Override // size의 크기를 하나의 쿼리로 진행합니다. (저희는 8개로 테스트했기 때문에 8입니다)
            public int getBatchSize() {
                return postImages.size();
            }
        });
    }

}
